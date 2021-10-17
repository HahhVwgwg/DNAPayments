package com.dnapayments.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sample(
    var status: Boolean?,
    var message: String?,
) : Parcelable