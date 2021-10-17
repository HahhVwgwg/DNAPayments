package com.dnapayments.data.model

import com.google.gson.annotations.SerializedName


data class Quizzes(

    @SerializedName("id") var id: Int,
    @SerializedName("lesson_id") var lessonId: Int,
    @SerializedName("question") var question: String,
    @SerializedName("created_at") var createdAt: String,
    @SerializedName("updated_at") var updatedAt: String,
    @SerializedName("options") var options: List<Options>,
    )