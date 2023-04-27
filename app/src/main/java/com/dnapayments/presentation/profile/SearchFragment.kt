package com.dnapayments.presentation.profile

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import com.dnapayments.R
import com.dnapayments.databinding.FragmentSearchBinding
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.android.ext.android.inject

class SearchFragment :
    BaseBindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val vm: SearchViewModel by inject()
    override fun initViews(savedInstanceState: Bundle?) {
        initSearchView()
    }

    private fun initSearchView() = with(binding) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                callSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                callSearch(newText)
                return true
            }

            fun callSearch(query: String) {
                vm.onSearch(query)
            }
        })
    }
}