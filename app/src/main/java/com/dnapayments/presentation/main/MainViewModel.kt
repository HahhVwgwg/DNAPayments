package com.dnapayments.presentation.main

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.dnapayments.R
import com.dnapayments.data.model.CardOtp
import com.dnapayments.data.model.ProfileOtp
import com.dnapayments.data.model.SimpleResult
import com.dnapayments.data.repository.MainRepository
import com.dnapayments.utils.*
import com.dnapayments.utils.Constants.CALCULATE_COMMISSION
import com.dnapayments.utils.Constants.CHOOSE_CARD
import com.dnapayments.utils.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : BaseViewModel() {
    val cardId = NonNullObservableField(-1)
    var profile = ObservableField<ProfileOtp>()
    var selectedCard = NonNullObservableField(CHOOSE_CARD)
    val success = SingleLiveData<Boolean>()
    val cards = SingleLiveData<List<CardOtp>>()
    val amount = NonNullObservableField("")
    val commission = NonNullObservableField("")
    val withdrawText = NonNullObservableField(CALCULATE_COMMISSION)
    val checkCommission = NonNullObservableField(true)
    val viewState = NonNullObservableField(true)


    fun onClick() {
        if (checkCommission.get()) {
            getCommission()
        } else {
            withdraw()
        }
    }

    private fun getCommission() {
        when {
            amount.get().isEmpty() -> R.string.error_minimum_amount
            !amount.get().isInt() -> amount.set("")
            amount.get().toInt() < 200 -> error.value =
                R.string.error_minimum_amount
            profile.get()!!.walletBalance - amount.get().toInt() < 100 -> error.value =
                R.string.error_minimum_amount_left
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

    private fun withdraw() {
        when {
            cardId.get() == -1 -> error.value = R.string.choose_card_first
            else -> {
                viewModelScope.launch {
                    isLoading.value = true
                    val response = repository.withDraw(amount.get(), cardId.get())
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
        fetchProfile()
    }

    fun getNews() {
        viewModelScope.launch {
            val response = repository.getNews()
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
            val response = repository.fetchProfile("android", "2.2.2")
            success.value = true
            when (response) {
                is SimpleResult.Success -> {
                    response.data.let {
                        profile.set(it)
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