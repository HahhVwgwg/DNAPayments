package com.dnapayments.presentation.knowledge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dnapayments.data.model.NotificationElement
import com.dnapayments.data.model.SimpleResult
import com.dnapayments.data.repository.MainRepository
import com.dnapayments.utils.SingleLiveData
import com.dnapayments.utils.base.BaseViewModel
import kotlinx.coroutines.launch

class KnowledgeViewModel(private val repository: MainRepository) : BaseViewModel() {
    val knowledgeList = MutableLiveData<ArrayList<NotificationElement>>()
    val success = SingleLiveData<Boolean>()

    init {
        fetchKnowBase()
    }

    fun fetchKnowBase() {
        viewModelScope.launch {
            success.value = false
            val response = repository.getKnowBase()
            success.value = true
            when (response) {
                is SimpleResult.Success -> {
                    knowledgeList.value = response.data as ArrayList<NotificationElement>
                }
                is SimpleResult.Error -> {
                    errorString.value = response.errorMessage
                }
                is SimpleResult.NetworkError -> {
                    showNetworkError.value = true
                }
            }
        }
    }
}