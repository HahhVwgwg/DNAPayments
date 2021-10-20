package com.dnapayments.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileOtp(
    @SerializedName("id")
    @Expose
    val id: Int = 0,

    @SerializedName("first_name")
    @Expose
    var firstName: String? = null,

    @SerializedName("last_name")
    @Expose
    var lastName: String? = null,

    val mobile: String? = null,

    @SerializedName("service_country")
    @Expose
    val serviceCountry: String? = null,

    @SerializedName("service_number")
    @Expose
    val serviceNumber: String? = null,

    @SerializedName("service_car")
    @Expose
    val serviceCar: String? = null,

    @SerializedName("service_color")
    @Expose
    val serviceColor: String? = null,

    @SerializedName("service_year")
    @Expose
    val serviceYear: String? = null,

    @SerializedName("service_model")
    @Expose
    val serviceModel: String? = null,

    @SerializedName("status")
    @Expose
    val status: Int = 0,

    @SerializedName("current_status")
    @Expose
    val currentStatus: String? = null,

    @SerializedName("working_status")
    @Expose
    val workingStatus: String? = null,

    @SerializedName("wallet_balance")
    @Expose
    var walletBalance: Int = 0,

    @SerializedName("payment_type")
    @Expose
    val paymentType: String? = null,

    @SerializedName("support_mobile")
    @Expose
    val supportMobile: String? = null,

    @SerializedName("park_mobile")
    @Expose
    val parkMobile: String? = null,

    @SerializedName("card_count")
    @Expose
    val cardCount: Int = 0,

    @SerializedName("fleet")
    @Expose
    val fleet: Fleet? = null,


    @SerializedName("force_update")
    @Expose
    val forceUpdate: Boolean = false,

    @SerializedName("url")
    @Expose
    val url: String? = null,

    @SerializedName("error")
    @Expose
    val error: String? = null,
) : Parcelable

