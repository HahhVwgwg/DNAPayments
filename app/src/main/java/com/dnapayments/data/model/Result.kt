package com.dnapayments.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val total: Int,
    val result: Int,
) : Parcelable
