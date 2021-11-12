package com.dnapayments.presentation.details

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.dnapayments.R
import com.dnapayments.databinding.FragmentDetailsBinding
import com.dnapayments.utils.Constants
import com.dnapayments.utils.base.BaseBindingFragment
import com.dnapayments.utils.vimeoextractor.OnVimeoExtractionListener
import com.dnapayments.utils.vimeoextractor.VimeoExtractor
import com.dnapayments.utils.vimeoextractor.VimeoVideo
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsFragment :
    BaseBindingFragment<FragmentDetailsBinding, DetailsViewModel>(R.layout.fragment_details) {
    override val vm: DetailsViewModel by viewModel()

    @SuppressLint("SetJavaScriptEnabled")
    override fun initViews(savedInstanceState: Bundle?) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val videoUrl = arguments?.getString(Constants.VIDEO_URL) ?: ""
        val lessonId = arguments?.getInt(Constants.LESSON_ID) ?: -1
        val passed = arguments?.getBoolean(Constants.PASSED) ?: false
        binding?.run {
            viewModel = vm
            vm.title.set(arguments?.getString(Constants.COURSE_TITLE) ?: "")


            back.setOnClickListener {
                onBackPressed()
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
        VimeoExtractor.getInstance()
            .fetchVideoWithURL(videoUrl, null, object : OnVimeoExtractionListener {
                override fun onSuccess(video: VimeoVideo) {
                    val videoStream = video.streams["720p"] //get 720p or whatever
                    videoStream?.let {
                        playVideo(it)
                    }
                }

                override fun onFailure(throwable: Throwable) {
                }
            })
    }

    fun playVideo(videoUrl: String) {
        activity?.runOnUiThread {
            binding?.run {
                fullscreenVideoView.videoUrl(videoUrl)?.enableAutoStart()
                fullscreenVideoView.addOnVideoPreparedListener {
                    videoLayout.visibility = View.VISIBLE
                    loader.visibility = View.GONE
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val currentOrientation = resources.configuration.orientation
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            Log.v("TAG", "Portrait !!!")
        }
    }

//    fun playVideo(videoStream: String) {
//        activity?.runOnUiThread {
//            hideLoader()
//
//            binding?.videoView?.run {
//                setVideoPath(videoStream)
//                requestFocus()
//                setOnPreparedListener { mp ->
//                    mp.isLooping = true
//                    start()
//                }
//                setMediaController(binding!!.mediaController)
//                setVideoViewCallback(object : VideoViewCallback {
//                    override fun onScaleChange(isFullscreen: Boolean) {
//                        println("isBack${isFullscreen}")
//                        if (isFullscreen) {
//                            val layoutParams: ViewGroup.LayoutParams =
//                                binding!!.videoLayout.layoutParams
//                            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
//                            this@DetailsFragment.cachedHeight = layoutParams.height
//                            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
//                            binding!!.videoLayout.layoutParams = layoutParams
//                            //GONE the unconcerned views to leave room for video and controller
//                        } else {
//                            val layoutParams: ViewGroup.LayoutParams =
//                                binding!!.videoLayout.layoutParams
//                            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
//                            layoutParams.height = this@DetailsFragment.cachedHeight
//                            binding!!.videoLayout.layoutParams = layoutParams
//                        }
//                    }
//
//                    override fun onPause(mediaPlayer: MediaPlayer?) { // Video pause
//                    }
//
//                    override fun onStart(mediaPlayer: MediaPlayer?) { // Video start/resume to play
//                    }
//
//                    override fun onBufferingStart(mediaPlayer: MediaPlayer?) { // steam start loading
//                    }
//
//                    override fun onBufferingEnd(mediaPlayer: MediaPlayer?) { // steam end loading
//                    }
//                })
//            }
//
//        }
//    }
}