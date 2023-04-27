package com.dnapayments.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dnapayments.data.repository.SearchRepository
import com.dnapayments.domain.presentation.SearchResult
import com.dnapayments.utils.base.BaseViewModel
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository) : BaseViewModel() {
    val searchResults = MutableLiveData<List<SearchResult>>()
    init {

    }

    fun onSearch(search: String) {
        viewModelScope.launch {
            val response = searchRepository.makeQuery(search)
        }
    }
}
