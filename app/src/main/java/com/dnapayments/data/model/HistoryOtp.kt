package com.dnapayments.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryOtp(
    @SerializedName("wallet_details")
    val walletDetails: WalletDetails,

    @SerializedName("error")
    var error: String? = null,

    ) : Parcelable