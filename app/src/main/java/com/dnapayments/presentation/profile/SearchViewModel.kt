package com.dnapayments.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dnapayments.data.repository.SearchRepository
import com.dnapayments.domain.network.SimpleResult
import com.dnapayments.domain.presentation.SearchResult
import com.dnapayments.utils.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository) : BaseViewModel() {
    val searchResults = MutableLiveData<List<SearchResult>>()
    fun onSearch(search: String) {
        viewModelScope.launch {
            when (val response = searchRepository.makeQuery(search)) {
                is SimpleResult.Error -> {}
                is SimpleResult.NetworkError -> {}
                is SimpleResult.Success -> response.data.let {
                    searchResults.value = it
                }
            }
        }
    }
}
