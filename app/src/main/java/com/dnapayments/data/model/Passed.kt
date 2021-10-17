package com.dnapayments.data.model

import com.google.gson.annotations.SerializedName

data class Passed(
    @SerializedName("passed") var passed: Int,
)