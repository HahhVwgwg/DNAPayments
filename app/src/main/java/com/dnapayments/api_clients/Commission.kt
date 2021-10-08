package com.dnapayments.api_clients

import com.google.gson.annotations.SerializedName

data class Commission(
    @SerializedName("amount_sent")
    val amountSent: Int,

    @SerializedName("amount_fee_driver")
    val amountFee: Int,

    @SerializedName("amount_total")
    val amountTotal: Int,

    @SerializedName("amount_park")
    val amountPark: Int,

    @SerializedName("free_transaction")
    val freeTransaction: Int,

    @SerializedName("error")
    val error: String? = null,
)
