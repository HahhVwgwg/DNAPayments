package com.dnapayments.data.model

import com.google.gson.annotations.SerializedName

data class ParkElement(
    val id: Long,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    val email: String? = null,
    val mobile: String,

    @SerializedName("service_country")
    val serviceCountry: String,

    @SerializedName("service_number")
    val serviceNumber: String,

    @SerializedName("service_car")
    val serviceCar: String,

    @SerializedName("service_color")
    val serviceColor: String,

    @SerializedName("service_year")
    val serviceYear: String,

    @SerializedName("service_model")
    val serviceModel: String,

    val password: String,
    val avatar: String? = null,

    @SerializedName("yandex_account_id")
    val yandexAccountID: String,

    val rating: String,
    val status: Long,

    @SerializedName("current_status")
    val currentStatus: String,

    @SerializedName("working_status")
    val workingStatus: String,

    val fleet: Long,
    val latitude: String? = null,
    val longitude: String? = null,

    @SerializedName("login_by")
    val loginBy: String? = null,

    val otp: Long,

    @SerializedName("wallet_balance")
    val walletBalance: Double,

    @SerializedName("closed_balance")
    val closedBalance: Long,

    @SerializedName("referral_unique_id")
    val referralUniqueID: String? = null,

    @SerializedName("qrcode_url")
    val qrcodeURL: String? = null,

    @SerializedName("remember_token")
    val rememberToken: String? = null,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("park_name")
    val parkName: String,

    var selected: Boolean = false,
)