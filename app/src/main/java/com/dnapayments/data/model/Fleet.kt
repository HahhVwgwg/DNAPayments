package com.dnapayments.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fleet(
    @SerializedName("park_name")
    @Expose
    val park_name: String? = null,
) : Parcelable