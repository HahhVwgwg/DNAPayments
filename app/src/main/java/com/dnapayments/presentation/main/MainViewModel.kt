package com.dnapayments.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dnapayments.data.model.Zhyrau
import com.dnapayments.data.repository.StoriesRepository
import com.dnapayments.data.repository.ZhyrauRepository
import com.dnapayments.domain.network.SimpleResult
import com.dnapayments.domain.presentation.Story
import com.dnapayments.utils.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val storiesRepository: StoriesRepository,
    private val zhyrauRepository: ZhyrauRepository
) : BaseViewModel() {

    val zhyrauList = MutableLiveData<List<Zhyrau>>()

    init {
        fetchZhyrauList()
    }

    private fun isStoriesShownToUser(idStory: String): Boolean =
        storiesRepository.isStoriesShownToUser(idStory)

    fun storiesShownList(list: List<Story>): List<Boolean> {
        val storiesShown = arrayListOf<Boolean>()
        for (parent in list) {
            storiesShown.add(isStoriesShownToUser(parent.id))
        }
        return storiesShown
    }

    private fun fetchZhyrauList() {
        viewModelScope.launch {
            when (val response = zhyrauRepository.fetchZhyrauList()) {
                is SimpleResult.Error -> {}
                is SimpleResult.NetworkError -> {}
                is SimpleResult.Success -> response.data.let {
                    zhyrauList.value = it
                }
            }
        }
    }
}
