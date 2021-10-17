package com.dnapayments.utils.base

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnapayments.utils.SingleLiveData

@SuppressLint("CheckResult")
abstract class BaseViewModel : ViewModel() {
    var isLoading = MutableLiveData<Boolean>()
    var isRefreshing = SingleLiveData<Boolean>()
    val error = SingleLiveData<Int>()
    val errorString = SingleLiveData<String>()
    val showNetworkError = SingleLiveData<Boolean>()
    var toastMsg = MutableLiveData<String>()
    var toastMsgId = MutableLiveData<Int>()


    fun onError(t: Throwable) {
        isLoading.value = false
        isRefreshing.value = false

        t.message?.let {
            toastMsg.postValue(it)
        }
    }

    fun onSuccess() {
        isLoading.value = false
        isRefreshing.value = false
    }

}