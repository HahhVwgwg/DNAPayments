package com.dnapayments.api_clients

import com.dnapayments.data.model.LoginResponse
import com.dnapayments.data.model.SampleResponseModal
import kotlinx.coroutines.Deferred
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface ILoginService {

    @FormUrlEncoded
    @POST("/api/v1/login")
    fun loginAsync(
        @FieldMap params: HashMap<String, Any>,
    ): Deferred<SampleResponseModal<LoginResponse>>

}