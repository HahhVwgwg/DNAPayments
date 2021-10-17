package com.dnapayments.api_clients

import androidx.annotation.Nullable
import com.dnapayments.data.model.*
import kotlinx.coroutines.Deferred
import retrofit2.http.*
import java.util.*

interface IMainService {

    /*  CharacterList  */
    @Headers("Content-Type: application/json")
    @GET("api/characters")
    fun getCharacterListAsync(): Deferred<List<Character>>


    @Headers("Content-Type: application/json")
    @GET("/api/v1/courses")
    fun getCoursesAsync(): Deferred<SampleResponseModal<CoursesResponse>>

    @GET("/api/v1/courses/{id}/lessons/results")
    fun getLessonsAsync(
        @Path("id") id: Int,
    ): Deferred<SampleResponseModal<LessonResponse>>

    @GET("/api/v1/quizes/options")
    fun getQuizzesAsync(@Query("lessonId") lessonId: Int): Deferred<SampleResponseModal<QuizResponse>>

    @FormUrlEncoded
    @POST("/api/v1/quizes")
    fun saveQuizResultAsync(
        @FieldMap params: HashMap<String, Any>,
    ): Deferred<Sample>
}