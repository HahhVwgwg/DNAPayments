package com.dnapayments.presentation.news_details

import android.os.Bundle
import com.dnapayments.R
import com.dnapayments.databinding.FragmentNewsDetailsBinding
import com.dnapayments.utils.Constants.NEWS_ITEM
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsDetailsFragment :
    BaseBindingFragment<FragmentNewsDetailsBinding, NewsDetailsViewModel>(R.layout.fragment_news_details) {
    override val vm: NewsDetailsViewModel by viewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            vm.newsDetails.set(arguments?.getParcelable(NEWS_ITEM))
            viewModel = vm
            back.setOnClickListener {
                onBackPressed()
            }
        }
    }
}