package com.dnapayments.presentation.details

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.dnapayments.R
import com.dnapayments.databinding.FragmentDetailsBinding
import com.dnapayments.presentation.activity.MainActivity
import com.dnapayments.utils.Constants
import com.dnapayments.utils.base.BaseBindingFragment
import com.dnapayments.utils.vimeoextractor.OnVimeoExtractionListener
import com.dnapayments.utils.vimeoextractor.VimeoExtractor
import com.dnapayments.utils.vimeoextractor.VimeoVideo
import com.universalvideoview.UniversalVideoView.VideoViewCallback
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsFragment :
    BaseBindingFragment<FragmentDetailsBinding, DetailsViewModel>(R.layout.fragment_details) {
    override val vm: DetailsViewModel by viewModel()
    private var cachedHeight = 0

    @SuppressLint("SetJavaScriptEnabled")
    override fun initViews(savedInstanceState: Bundle?) {
        val videoUrl = arguments?.getString(Constants.VIDEO_URL) ?: ""
        val lessonId = arguments?.getInt(Constants.LESSON_ID) ?: -1
        val passed = arguments?.getBoolean(Constants.PASSED) ?: false
        binding?.run {
            viewModel = vm
//            showLoader()
            vm.title.set(arguments?.getString(Constants.COURSE_TITLE) ?: "")

            //webview
            playerViewLayout.clipToOutline = true
            webView.settings.javaScriptEnabled = true
            webView.setBackgroundColor(0)
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                    view?.loadUrl(videoUrl)
                    return true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
//                    hideLoader()
                    super.onPageFinished(view, url)
                }
            }
//            webView.loadUrl(videoUrl)
            webView.visibility = View.INVISIBLE
            back.setOnClickListener {
                if (activity is MainActivity) {
                    (activity as MainActivity).onBack()
                }
            }
            passQuiz.setOnClickListener {
                findNavController().navigate(R.id.action_details_to_quiz, Bundle().apply {
                    putInt(Constants.LESSON_ID, lessonId)
                    putBoolean(Constants.PASSED, passed)
                })
            }
            initializePlayer(videoUrl)
        }
    }

    private fun initializePlayer(videoUrl: String) {
        showLoader()
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        VimeoExtractor.getInstance()
            .fetchVideoWithURL(videoUrl, null, object : OnVimeoExtractionListener {
                override fun onSuccess(video: VimeoVideo) {
                    val videoStream = video.streams["720p"] //get 720p or whatever
                    videoStream?.let {
                        playVideo(videoStream)
                    }
                }

                override fun onFailure(throwable: Throwable) {
                }
            })
    }

    fun playVideo(videoStream: String) {
        activity?.runOnUiThread {
            hideLoader()

            binding?.videoView?.run {
                setVideoPath(videoStream)
                requestFocus()
                setOnPreparedListener { mp ->
                    mp.isLooping = true
                    start()
                }
                setMediaController(binding!!.mediaController)
                setVideoViewCallback(object : VideoViewCallback {
                    override fun onScaleChange(isFullscreen: Boolean) {
                        println("isBack${isFullscreen}")
                        if (isFullscreen) {
                            val layoutParams: ViewGroup.LayoutParams =
                                binding!!.videoLayout.layoutParams
                            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                            this@DetailsFragment.cachedHeight = layoutParams.height
                            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                            binding!!.videoLayout.layoutParams = layoutParams
                            //GONE the unconcerned views to leave room for video and controller
                        } else {
                            val layoutParams: ViewGroup.LayoutParams =
                                binding!!.videoLayout.layoutParams
                            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                            layoutParams.height = this@DetailsFragment.cachedHeight
                            binding!!.videoLayout.layoutParams = layoutParams
                        }
                    }

                    override fun onPause(mediaPlayer: MediaPlayer?) { // Video pause
                    }

                    override fun onStart(mediaPlayer: MediaPlayer?) { // Video start/resume to play
                    }

                    override fun onBufferingStart(mediaPlayer: MediaPlayer?) { // steam start loading
                    }

                    override fun onBufferingEnd(mediaPlayer: MediaPlayer?) { // steam end loading
                    }
                })
            }

        }
    }
}