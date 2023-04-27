package com.dnapayments.presentation.details

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dnapayments.R
import com.dnapayments.presentation.profile.SearchAdapter
import com.dnapayments.presentation.profile.SearchViewModel
import com.dnapayments.utils.base.BaseHomeBottomFragment
import org.koin.android.ext.android.inject

class MultiChoiceBottomFragment(private val query: String) :
    BaseHomeBottomFragment(R.layout.bottom_query_results) {

    private val vm: SearchViewModel by inject()

    private val searchAdapter = SearchAdapter {

    }

    override fun initViews(view: View?, savedInstanceState: Bundle?) {
        activity?.let {
            view?.run {

                vm.onSearch(query)
                vm.searchResults.observe(viewLifecycleOwner) {
                    searchAdapter.setData(it)
                }

                findViewById<RecyclerView>(R.id.recycler_view).adapter = searchAdapter
            }
        }
    }

    companion object {
        const val QUERY = "QUERY"
    }
}