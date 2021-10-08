package com.dnapayments.presentation.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dnapayments.data.model.SimpleResult
import com.dnapayments.data.model.WalletTransation
import com.dnapayments.data.repository.MainRepository
import com.dnapayments.utils.SingleLiveData
import com.dnapayments.utils.base.BaseViewModel
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: MainRepository) : BaseViewModel() {
    val walletTransactions = MutableLiveData<ArrayList<WalletTransation>>()
    val success = SingleLiveData<Boolean>()

    init {
        fetchHistory()
    }

    fun fetchHistory() {
        viewModelScope.launch {
            success.value = false
            val response = repository.fetchHistory()
            success.value = true
            when (response) {
                is SimpleResult.Success -> {
                    walletTransactions.value =
                        response.data.walletTransation as ArrayList<WalletTransation>
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