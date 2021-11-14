package com.dnapayments.presentation.main

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dnapayments.BuildConfig
import com.dnapayments.R
import com.dnapayments.data.model.CardOtp
import com.dnapayments.data.model.NotificationElement
import com.dnapayments.data.model.ProfileOtp
import com.dnapayments.data.model.SimpleResult
import com.dnapayments.data.repository.MainRepository
import com.dnapayments.utils.*
import com.dnapayments.utils.Constants.CALCULATE_COMMISSION
import com.dnapayments.utils.Constants.CHOOSE_CARD
import com.dnapayments.utils.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : BaseViewModel() {
    //main page
    val viewState = NonNullObservableField(true)
    var profile = ObservableField<ProfileOtp>()
    val mainBottomSheetSelectedItm = SingleLiveData<Int>()
    val newsList = MutableLiveData<List<NotificationElement>>()
    val news = mutableListOf<NotificationElement>()

    //withdraw callback
    val showBottomSheet = SingleLiveData<Boolean>()
    val showUpdateError = MutableLiveData<String>()
    val success = SingleLiveData<Boolean>()
    val cards = SingleLiveData<List<CardOtp>>()

    //withdraw ui
    var selectedCard = NonNullObservableField(CHOOSE_CARD)
    val amount = NonNullObservableField("")
    val commission = NonNullObservableField("")
    val cardId = NonNullObservableField(-1)
    val withdrawText = NonNullObservableField(CALCULATE_COMMISSION)
    val checkCommission = NonNullObservableField(true)

    val onPinCallback = SingleLiveData<Boolean>()


    fun onClick() {
        if (checkCommission.get()) {
            getCommission()
        } else {
            showBottomSheet.value = true
        }
    }

    private fun getCommission() {
        when {
            amount.get().isEmpty() -> R.string.error_minimum_amount
            !amount.get().isInt() -> amount.set("")
            amount.get().toInt() < 200 -> error.value =
                R.string.error_minimum_amount
            profile.get() == null -> error.value = R.string.something_went_wrong
            amount.get().toInt() > profile.get()!!.walletBalance -> error.value =
                R.string.error_not_enough_money
            else -> {
                viewModelScope.launch {
                    isLoading.value = true
                    val response = repository.getCommission(amount.get())
                    isLoading.value = false
                    when (response) {
                        is SimpleResult.Success -> {
                            response.data.let {
                                withdrawText.set(it.amountFee.sendFormat())
                                commission.set(it.amountSent.commissionFormat())
                                checkCommission.set(false)
                            }
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

    }

    fun withdraw() {
        when {
            cardId.get() == -1 -> error.value = R.string.choose_card_first
            else -> {
                viewModelScope.launch {
                    isLoading.value = true
                    val response = repository.withDraw(amount.get(), cardId.get())
                    amount.set("0")
                    isLoading.value = false
                    when (response) {
                        is SimpleResult.Success -> {
                            response.data.let {
                                errorString.value = it.pending

                                onAmountChanged()
                            }
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
    }

    fun getCardList() {
        viewModelScope.launch {
            isLoading.value = true
            val response = repository.getCardList()
            isLoading.value = false
            when (response) {
                is SimpleResult.Success -> {
                    response.data.let {
                        cards.value = it
                    }
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

    fun deleteCard(id: Int) {
        viewModelScope.launch {
            isLoading.value = true
            val response = repository.deleteCard(id)
            isLoading.value = false
            when (response) {
                is SimpleResult.Success -> {
                    error.value = R.string.card_was_deleted
                    selectedCard.set(CHOOSE_CARD)
                    cardId.set(-1)
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

    fun onAmountChanged() {
        commission.set("")
        withdrawText.set(CALCULATE_COMMISSION)
        checkCommission.set(true)
    }

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            success.value = false
            when (val response = repository.getNews()) {
                is SimpleResult.Success -> {
                    response.data.let {
                        news.clear()
                        news.addAll(it)
                        newsList.value = it
                    }
                    fetchProfile()

                }
                is SimpleResult.Error -> {
                    success.value = true
                    errorString.value = response.errorMessage
                }
                is SimpleResult.NetworkError -> {
                    success.value = true
                    showNetworkError.value = true
                }
            }
        }
    }

    fun fetchProfile() {
        viewModelScope.launch {
            profile.set(ProfileOtp().apply {
                firstName = ""
                lastName = ""
                walletBalance = 0
            })
            success.value = false
            val response = repository.fetchProfile("android", BuildConfig.VERSION_NAME)
            success.value = true
            when (response) {
                is SimpleResult.Success -> {
                    response.data.let {
                        if (it.forceUpdate) {
                            showUpdateError.value = it.url!!
                        } else {
                            profile.set(it)
                        }
                    }
                }
                is SimpleResult.Error -> {
                    errorString.value = response.errorMessage
                }
                else -> {
                    showNetworkError.value = true
                }
            }
        }
    }
}