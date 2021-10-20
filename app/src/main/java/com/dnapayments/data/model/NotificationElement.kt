package com.dnapayments.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationElement(
    val id: Long,

    @SerializedName("notify_type")
    val notifyType: String,

    val image: String,
    val title: String,
    val description: String,

    @SerializedName("expiry_date")
    val expiryDate: String,

    val status: String,

    @SerializedName("viewed_users_json")
    val viewedUsersJSON: String,

    @SerializedName("error")
    val error: String,
) : Parcelable