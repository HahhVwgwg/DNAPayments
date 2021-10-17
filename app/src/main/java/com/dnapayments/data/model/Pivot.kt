package com.dnapayments.data.model

import com.google.gson.annotations.SerializedName

   
data class Pivot (

   @SerializedName("user_id") var userId : Int,
   @SerializedName("course_id") var courseId : Int

)