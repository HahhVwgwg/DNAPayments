package com.dnapayments.data.model

import com.google.gson.annotations.SerializedName

class CardOtp {
    @SerializedName("id")
    val id = 0

    @SerializedName("user_id")
    val userID = 0

    @SerializedName("last_four")
    var lastFour: String? = null

    @SerializedName("card_name")
    val cardName: String? = null

    @SerializedName("brand")
    val brand: String? = null

    @SerializedName("is_default")
    val isDefault = 0

    @SerializedName("created_at")
    val createdAt: String? = null

    override fun toString(): String {
        return "CardOtp(id=$id, userID=$userID, lastFour=$lastFour, cardName=$cardName, brand=$brand, isDefault=$isDefault, createdAt=$createdAt)"
    }

}