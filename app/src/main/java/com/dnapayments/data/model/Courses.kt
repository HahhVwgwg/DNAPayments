package com.dnapayments.data.model

import com.google.gson.annotations.SerializedName

data class Courses(

    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("order") var order: String,
    @SerializedName("created_at") var createdAt: String,
    @SerializedName("updated_at") var updatedAt: String,
    @SerializedName("lessons_count") var lessonsCount: Int,
    @SerializedName("pivot") var pivot: Pivot,
    @SerializedName("lessons") var lessons: List<Lessons>,
)

