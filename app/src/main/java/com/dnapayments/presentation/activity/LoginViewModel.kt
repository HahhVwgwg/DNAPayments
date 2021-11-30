package com.dnapayments.presentation.activity

import androidx.lifecycle.viewModelScope
import com.dnapayments.R
import com.dnapayments.utils.NonNullObservableField
import com.dnapayments.utils.SingleLiveData
import com.dnapayments.utils.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel :
    BaseViewModel() {
    val success = SingleLiveData<Boolean>()
    val email = NonNullObservableField("")
    val nameAndSurname = NonNullObservableField("")
    val password = NonNullObservableField("")
    val passwordRepeat = NonNullObservableField("")

    fun login() {
        when {
            email.get().length < 4 -> error.value = R.string.error_email_length
            nameAndSurname.get().length < 4 -> error.value = R.string.error_credential_length
            password.get().length < 4 -> error.value = R.string.error_password_length
            else -> {
                viewModelScope.launch {
                    success.value = true
                }

            }
        }
    }

    fun register() {
        when {
            email.get().length < 4 -> error.value = R.string.error_email_length
            password.get().length < 4 -> error.value = R.string.error_password_length
            nameAndSurname.get().length < 4 -> error.value = R.string.error_credential_length
            passwordRepeat.get().length < 4 -> error.value = R.string.error_password_length
            passwordRepeat.get() != password.get() -> error.value = R.string.password_not_same
            else -> {
                success.value = true
//                viewModelScope.launch {
//                    isLoading.value = true
//                    val response = repository.login(email.get(), password.get())
//                    isLoading.value = false
//                    when (response) {
//                        is SimpleResult.Success -> {
//                            response.data.let {
//                                prefsAuth.saveAccessToken(it.token)
//                                prefsAuth.setAuthorized(true)
//                                prefsAuth.saveUser(it.user)
//                                success.value = true
//                            }
//                        }
//                        is SimpleResult.Error -> {
//                            errorString.value = response.errorMessage
//                        }
//                        is SimpleResult.NetworkError -> {
//                            showNetworkError.value = true
//                        }
//                    }
//                }

            }
        }
    }
}
