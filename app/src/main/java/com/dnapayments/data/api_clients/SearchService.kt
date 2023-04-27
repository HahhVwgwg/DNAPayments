package com.dnapayments.data.api_clients

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchService {

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/search")
    suspend fun getSearchResultsAsync(
        @Query("q") query: String
    ): ResponseBody

}