package com.dnapayments.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalletTransation(
    val id: Long,

    @SerializedName("from_id")
    val fromID: String,

    @SerializedName("fleet_id")
    val fleetID: Long,

    @SerializedName("card_number")
    val cardNumber: String? = null,

    @SerializedName("card_name")
    val cardName: String? = null,

    @SerializedName("transaction_id")
    val transactionID: Long,

    @SerializedName("transaction_alias")
    val transactionAlias: String,

    @SerializedName("transaction_desc")
    val transactionDesc: String,

    val type: String,
    val amount: Double,

    @SerializedName("open_balance")
    val openBalance: Double,

    @SerializedName("close_balance")
    val closeBalance: Double,

    @SerializedName("payment_mode")
    val paymentMode: String,

    val status: String,

    @SerializedName("created_at")
    val createdAt: String,
) : Parcelable