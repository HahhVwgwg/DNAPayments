package com.dnapayments.presentation.profile

import android.os.Bundle
import com.dnapayments.R
import com.dnapayments.databinding.FragmentSearchBinding
import com.dnapayments.utils.afterTextChangedDebounce
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.android.ext.android.inject

class SearchFragment :
    BaseBindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val vm: SearchViewModel by inject()

    private val searchAdapter = SearchAdapter {

    }

    override fun initViews(savedInstanceState: Bundle?) {
        initSearchView()
        initRecyclerView()
        vm.searchResults.observe(viewLifecycleOwner) {
            searchAdapter.setData(it)
        }
    }

    private fun initRecyclerView() = with(binding) {
        recyclerView.adapter = searchAdapter
    }

    private fun initSearchView() = with(binding) {
        searchView.afterTextChangedDebounce {
            vm.onSearch(it)
        }
    }
}