package com.dnapayments.presentation.registration

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dnapayments.R
import com.dnapayments.data.Resource
import com.dnapayments.data.model.OtpResponse
import com.dnapayments.data.model.TokenOtp
import com.dnapayments.data.repository.AuthorizationRepository
import com.dnapayments.utils.NonNullObservableField
import com.dnapayments.utils.base.BaseViewModel
import kotlinx.coroutines.launch


class AuthorizationViewModel(private val repository: AuthorizationRepository) : BaseViewModel() {
    val otpResponse = MutableLiveData<OtpResponse>()
    val tokenOtp = MutableLiveData<TokenOtp>()
    val spendTime = ObservableField<SpendTime>()
    val phoneNumber = NonNullObservableField("")
    val loading = ObservableField(false)

    fun getOtpByPhoneNumber(phone: String) {
        when {
            phoneNumber.get().isEmpty() -> error.value = R.string.please_enter_phone_number
            phoneNumber.get().trim().length != 10 -> error.value = R.string.enter_phone_number_right
            phoneNumber.get().trim() == "7471053955" || phoneNumber.get()
                .trim() == "7052664639" -> error.value = R.string.your_phone_number_is_blocked
            else -> {
                viewModelScope.launch {
                    loading.set(true)
                    val response = repository.getOtpByPhoneNumber(phone)
                    loading.set(false)
                    when (response.status) {
                        Resource.Status.SUCCESS -> {
                            response.data?.let {
                                otpResponse.value = it
                            }
                        }
                        Resource.Status.ERROR -> {
                            errorString.value = response.errorMessage

                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }

    fun loginByOtp(deviceToken: String, deviceId: String, otp: String, mobile: String) {
        loading.set(true)
        viewModelScope.launch {
            val response = repository.loginByOtp(deviceToken, deviceId, otp, mobile)
            loading.set(false)
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.let {
                        tokenOtp.value = it
                    }
                }
                Resource.Status.ERROR -> {
                    errorString.value = response.errorMessage
                }
                else -> {

                }
            }
        }
    }
}

data class SpendTime(
    val status: String,
    val background: Boolean,
)