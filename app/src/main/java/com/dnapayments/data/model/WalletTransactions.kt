package com.dnapayments.data.model

import com.google.gson.annotations.SerializedName

class WalletTransactions(
    @SerializedName("wallet_transation")
    val walletTransation: List<WalletTransation>,

    @SerializedName("wallet_balance")
    val walletBalance: Int,

    @SerializedName("error")
    val error: String? = null,
) {
    override fun toString(): String {
        return "WalletTransactions(walletTransation=$walletTransation, walletBalance=$walletBalance)"
    }
}