package com.dnapayments.data.model

import com.google.gson.annotations.SerializedName


data class Options(
    @SerializedName("id") var id: Int,
    @SerializedName("quiz_id") var quizId: Int,
    @SerializedName("option") var option: String,
    @SerializedName("is_correct") var isCorrect: Int,
    @SerializedName("created_at") var createdAt: String,
    @SerializedName("updated_at") var updatedAt: String,
    var selected: Boolean = false,
) {
    override fun toString(): String {
        return "Options(id=$id, option='$option', selected=$selected)"
    }
}