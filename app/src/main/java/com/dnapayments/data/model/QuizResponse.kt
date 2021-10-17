package com.dnapayments.data.model

import com.google.gson.annotations.SerializedName

   
data class QuizResponse (

   @SerializedName("quizes") var quizes : List<Quizzes>

)