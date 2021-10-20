package com.dnapayments.presentation.news

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dnapayments.R
import com.dnapayments.data.model.NotificationElement
import com.dnapayments.databinding.FragmentNewsBinding
import com.dnapayments.presentation.main.MainViewModel
import com.dnapayments.utils.Constants.NEWS_ITEM
import com.dnapayments.utils.Constants.NEWS_LIST
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment :
    BaseBindingFragment<FragmentNewsBinding, MainViewModel>(R.layout.fragment_news) {
    override val vm: MainViewModel by viewModel()
    private var newsAdapter: NewsAdapter? = null
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.run {
            newsAdapter = NewsAdapter {
                findNavController().navigate(R.id.action_news_to_news_details, Bundle().apply {
                    putParcelable(NEWS_ITEM, it)
                })
            }
            recyclerView.adapter = newsAdapter
            arguments?.getParcelableArrayList<NotificationElement>(NEWS_LIST)?.let { list ->
                newsAdapter?.setData(list)
            }
            back.setOnClickListener {
                onBackPressed()
            }
        }
    }
}