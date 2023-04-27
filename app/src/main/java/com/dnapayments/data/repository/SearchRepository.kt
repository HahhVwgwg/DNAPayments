package com.dnapayments.data.repository

import com.dnapayments.R
import com.dnapayments.data.Resource
import com.dnapayments.data.api_clients.SearchService
import com.dnapayments.domain.presentation.SearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepository(private val service: SearchService) {
    suspend fun makeQuery(query: String):
            Resource<String> {
        return try {
            withContext(Dispatchers.IO) {
                val result = service.getSearchResultsAsync(
                    query
                ).await()
                Resource.success(result)
            }
        } catch (e: Exception) {
            Resource.error(R.string.something_went_wrong)
        }
    }
}
