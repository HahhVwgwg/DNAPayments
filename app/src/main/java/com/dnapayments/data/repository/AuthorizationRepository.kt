package com.dnapayments.data.repository

import com.dnapayments.R
import com.dnapayments.api_clients.IAuthorizationService
import com.dnapayments.data.Resource
import com.dnapayments.data.model.APIError
import com.dnapayments.data.model.OtpResponse
import com.dnapayments.data.model.TokenOtp
import com.dnapayments.utils.simpleError
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


class AuthorizationRepository(private val service: IAuthorizationService) {
    suspend fun getOtpByPhoneNumber(phoneNumber: String):
            Resource<OtpResponse> {
        return try {
            withContext(Dispatchers.IO) {
                val result = service.getOtpAsync(
                    hashMapOf(
                        "mobile" to phoneNumber
                    )
                ).await()
                Resource.success(result)
            }
        } catch (e: Throwable) {
            e.simpleError()
        }
    }

    suspend fun loginByOtp(deviceToken: String, deviceId: String, otp: String, mobile: String):
            Resource<TokenOtp> {
        return try {
            withContext(Dispatchers.IO) {
                val result = service.loginByOtpAsync(
                    hashMapOf(
                        "mobile" to mobile,
                        "device_token" to deviceToken,
                        "device_id" to deviceId,
                        "otp" to otp,
                        "mobile" to mobile,
                        "device_type" to "android" //ToDo create build var for this field
                    )
                ).await()
                Resource.success(result)
            }
        } catch (e: Throwable) {
            e.simpleError()
        }
    }
}


