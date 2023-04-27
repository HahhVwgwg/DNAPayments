package com.dnapayments.domain.presentation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Story(
    val id: String,
    val image: String,
    val title: String
) : Parcelable