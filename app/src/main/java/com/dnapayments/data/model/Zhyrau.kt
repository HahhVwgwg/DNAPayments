package com.dnapayments.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Zhyrau(
    val id: Int? = null,
    val aqynName: String? = null,
    val aqynImage: String? = null,
    val name: String? = null,
    val content: String? = null
) : Parcelable