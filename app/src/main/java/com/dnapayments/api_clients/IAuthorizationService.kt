package com.dnapayments.api_clients

import com.dnapayments.data.model.Character
import com.dnapayments.data.model.OtpResponse
import com.dnapayments.data.model.ParkElement
import com.dnapayments.data.model.TokenOtp
import kotlinx.coroutines.Deferred
import retrofit2.http.*
import java.util.*

interface IAuthorizationService {

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("api/characters/{id}")
    fun getCharacterDetailedAsync(
        @Path(value = "id",
            encoded = true) charId: Int,
    ): Deferred<List<Character>>

    @FormUrlEncoded
    @POST("/api/provider/register_otp")
    fun getOtpAsync(@FieldMap params: HashMap<String, Any>): Deferred<OtpResponse>

    @FormUrlEncoded
    @POST("/api/provider/oauth/token")
    fun loginByOtpAsync(@FieldMap params: HashMap<String, Any>): Deferred<TokenOtp>

    @FormUrlEncoded
    @POST("/api/provider/oauth/token")
    fun getParksAsync(@FieldMap params: HashMap<String, Any>): Deferred<List<ParkElement>>
}

