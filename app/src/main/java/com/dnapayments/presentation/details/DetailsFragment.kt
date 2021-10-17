package com.dnapayments.presentation.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.dnapayments.R
import com.dnapayments.databinding.FragmentDetailsBinding
import com.dnapayments.utils.Constants
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment :
    BaseBindingFragment<FragmentDetailsBinding, DetailsViewModel>(R.layout.fragment_details) {
    override val vm: DetailsViewModel by viewModel()

    @SuppressLint("SetJavaScriptEnabled")
    override fun initViews(savedInstanceState: Bundle?) {
        val videoUrl = arguments?.getString(Constants.VIDEO_URL) ?: ""
        val lessonId = arguments?.getInt(Constants.LESSON_ID) ?: -1
        val passed = arguments?.getBoolean(Constants.PASSED) ?: false
        binding?.run {
            viewModel = vm
            showLoader()
            vm.title.set(arguments?.getString(Constants.COURSE_TITLE) ?: "")
            playerViewLayout.clipToOutline = true
            webView.settings.javaScriptEnabled = true
            webView.setBackgroundColor(0)
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(videoUrl)
                    return true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    hideLoader()
                    super.onPageFinished(view, url)
                }
            }
            webView.loadUrl(videoUrl)
            back.setOnClickListener {
                onBackPressed()
            }
            passQuiz.setOnClickListener {
                findNavController().navigate(R.id.action_details_to_quiz, Bundle().apply {
                    putInt(Constants.LESSON_ID, lessonId)
                    putBoolean(Constants.PASSED, passed)
                })
            }
        }
    }
}