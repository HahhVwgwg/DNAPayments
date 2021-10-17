package com.dnapayments.data.model

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("id") var id: Int,
    @SerializedName("login") var login: String,
    @SerializedName("created_at") var createdAt: String,
    @SerializedName("updated_at") var updatedAt: String,
    @SerializedName("name") var name: String,
    @SerializedName("surname") var surname: String,
    @SerializedName("city") var city: String,
    @SerializedName("birthday") var birthday: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("gender") var gender: String,
    @SerializedName("api_token") var apiToken: String,

    )