package com.dnapayments.presentation.history_detail

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.dnapayments.data.model.HistoryOtp
import com.dnapayments.data.model.SimpleResult
import com.dnapayments.data.repository.MainRepository
import com.dnapayments.utils.SingleLiveData
import com.dnapayments.utils.base.BaseViewModel
import com.dnapayments.utils.dateFormat
import com.dnapayments.utils.dateFormatSecond
import kotlinx.coroutines.launch

class HistoryDetailViewModel(private val repository: MainRepository) : BaseViewModel() {
    val success = SingleLiveData<Boolean>()
    val transaction = ObservableField<HistoryOtp>()
    val status = SingleLiveData<String>()


    fun fetchTransactionById(id: Int) {
        viewModelScope.launch {
            isLoading.value = true
            val response = repository.getTransactionById(id)
            isLoading.value = false
            when (response) {
                is SimpleResult.Success -> {
                    transaction.set(response.data.apply {
                        this.walletDetails.createDate =
                            this.walletDetails.createdAt.dateFormatSecond()
                        this.walletDetails.createdAt = this.walletDetails.createdAt.dateFormat()

                        status.value = this.walletDetails.status
                    })
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