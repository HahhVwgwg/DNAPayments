package com.dnapayments.api_clients

import com.dnapayments.data.model.*
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface IMainService {

    @GET("/api/provider/profile")
    fun getProfileAsync(
        @Query("device_type") device_type: String,
        @Query("version") version: String,
    ): Deferred<ProfileOtp>

    @FormUrlEncoded
    @POST("/api/provider/commission")
    fun getCommissionAsync(@FieldMap params: HashMap<String, Any>): Deferred<Commission>

    @FormUrlEncoded
    @POST("/api/provider/requestamount")
    fun withDrawAsync(@FieldMap params: HashMap<String, Any>): Deferred<MessageOtp>

    @POST("/api/provider/providercard")
    fun getCardListAsync(): Deferred<List<CardOtp>>

    @GET("/api/provider/wallettransaction")
    fun getWalletTransactionAsync(): Deferred<WalletTransactions>


    @GET("/api/provider/wallettransaction/details")
    fun getHistoryByIdAsync(@Query("id") id: Int): Deferred<HistoryOtp>

    @FormUrlEncoded
    @POST("/api/provider/providercard/store")
    fun addCardAsync(@FieldMap params: HashMap<String, Any>): Deferred<MessageOtp>

    @FormUrlEncoded
    @POST("/api/provider/providercard/delete")
    fun deleteCardAsync(@FieldMap params: HashMap<String, Any>): Deferred<MessageOtp>

    @GET("/api/provider/notifications")
    fun getNewsAsync(): Deferred<List<NotificationElement>>

}

