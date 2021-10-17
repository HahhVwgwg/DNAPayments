package com.dnapayments.data.repository

import androidx.annotation.Nullable
import com.dnapayments.api_clients.ILoginService
import com.dnapayments.data.model.LoginResponse
import com.dnapayments.data.model.SimpleResult
import com.dnapayments.utils.simpleError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(private val service: ILoginService) {

    suspend fun login(email: String, password: String):
            SimpleResult<LoginResponse> {
        return try {
            withContext(Dispatchers.IO) {
                service.loginAsync(
                    hashMapOf("login" to email, "password" to password)
                ).await().getSimpleResult()
            }
        } catch (e: Exception) {
            e.simpleError()
        }
    }
}
