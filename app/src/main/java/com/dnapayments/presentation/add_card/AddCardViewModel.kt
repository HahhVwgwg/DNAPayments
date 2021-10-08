package com.dnapayments.presentation.add_card

import androidx.lifecycle.viewModelScope
import com.dnapayments.R
import com.dnapayments.data.model.SimpleResult
import com.dnapayments.data.repository.MainRepository
import com.dnapayments.utils.NonNullObservableField
import com.dnapayments.utils.SingleLiveData
import com.dnapayments.utils.base.BaseViewModel
import kotlinx.coroutines.launch

class AddCardViewModel(private val repository: MainRepository) : BaseViewModel() {
    val success = SingleLiveData<Boolean>()
    val cardName = NonNullObservableField("")
    val cardId = NonNullObservableField("")
    val cardIdRepeat = NonNullObservableField("")

    fun addCard() {
        when {
            cardName.get().isEmpty() -> error.value = R.string.error_empty
            cardId.get().isEmpty() -> error.value = R.string.error_empty
            cardIdRepeat.get().isEmpty() -> error.value = R.string.error_empty
            cardIdRepeat.get() != cardId.get() -> error.value = R.string.notSameError
            cardIdRepeat.get().length < 19 -> error.value =
                R.string.error_length
            else -> {
                viewModelScope.launch {
                    isLoading.value = true
                    val response =
                        repository.addCard(cardId = cardId.get().replace("\\s".toRegex(), ""),
                            brand = if (cardId.get().startsWith("4")) "V" else "M",
                            cardName = cardName.get())
                    isLoading.value = false
                    when (response) {
                        is SimpleResult.Success -> {
                            success.value = true
                            errorString.value = response.data.success
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
}