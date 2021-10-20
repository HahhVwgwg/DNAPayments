package com.dnapayments.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalletDetails(
    val id: Long,

    @SerializedName("from_id")
    val fromID: String,

    @SerializedName("fleet_id")
    val fleetID: Long,

    @SerializedName("card_number")
    val cardNumber: String,

    @SerializedName("card_name")
    val cardName: String,

    @SerializedName("transaction_id")
    val transactionID: Long,

    @SerializedName("transaction_alias")
    val transactionAlias: String,

    @SerializedName("transaction_desc")
    val transactionDesc: String,

    @SerializedName("transaction_recept")
    val transactionRecept: String,

    val type: String,
    var amount: Double,

    @SerializedName("open_balance")
    var openBalance: Double,

    @SerializedName("close_balance")
    var closeBalance: Double,

    @SerializedName("payment_mode")
    val paymentMode: String,

    @SerializedName("commision_driver")
    val commissionDriver: Int,

    val status: String,

    @SerializedName("created_at")
    var createdAt: String,
    var createDate: String,
) : Parcelable
