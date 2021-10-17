package com.dnapayments.data.model

import com.google.gson.annotations.SerializedName


data class Lessons(
    @SerializedName("id") var id: Int,
    @SerializedName("course_id") var courseId: Int,
    @SerializedName("title") var title: String?,
    @SerializedName("video_url") var videoUrl: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("created_at") var createdAt: String?,
    @SerializedName("updated_at") var updatedAt: String?,
    @SerializedName("homework") var homework: String?,
    @SerializedName("result") var result: Passed?,
    var position: Int,
)

