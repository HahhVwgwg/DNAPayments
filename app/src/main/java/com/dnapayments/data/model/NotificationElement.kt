package com.dnapayments.data.model

import com.google.gson.annotations.SerializedName

class NotificationElement(
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
) {
    override fun toString(): String {
        return "NotificationElement(id=$id, notifyType='$notifyType', image='$image', title='$title', description='$description', expiryDate='$expiryDate', status='$status', viewedUsersJSON='$viewedUsersJSON', error='$error')"
    }
}