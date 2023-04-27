package com.dnapayments.data.api_clients

import com.dnapayments.domain.presentation.SearchResult
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchService {

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("/search")
    fun getSearchResultsAsync(
        @Query("q") query: String
    ): Deferred<String>

}