package com.dnapayments.data.model

import com.google.gson.annotations.SerializedName


data class LessonResponse(

    @SerializedName("courses") var courses: List<Courses>,

    )