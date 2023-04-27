package com.dnapayments.data.repository

import com.dnapayments.data.api_clients.SearchService
import com.dnapayments.domain.network.SimpleResult
import com.dnapayments.domain.presentation.SearchResult
import com.dnapayments.utils.simpleError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepository(private val service: SearchService) {
    suspend fun makeQuery(query: String):
            SimpleResult<List<SearchResult>> {
        return try {
            withContext(Dispatchers.IO) {
                val results = arrayListOf<SearchResult>()
                val result = service.getSearchResultsAsync(
                    query
                )
                val response = result.string() // insert HTML response string here
                val ulRegex =
                    Regex("""<ul class="sq-list">(.*?)</ul>""", RegexOption.DOT_MATCHES_ALL)
                val ulMatch = ulRegex.find(response)

                if (ulMatch != null) {
                    val ulString = ulMatch.groupValues[0]
                    val ulList = ulString.split("</li>")
                    for (item in ulList) {
                        // Extract span and link tags from item string using regex
                        val spanRegex = Regex("""<span class="counter">(.*?)</span>""")
                        val linkRegex = Regex("""<a href="(.*?)">(.*?)</a>""")
                        val headerRegex = Regex("<a[^>]*>([^<]*)<\\/a>")
                        val subHeaderRegex = Regex("—\\s*([^<]*)")
                        val headerMatch = headerRegex.find(item)
                        val subHeaderMatch = subHeaderRegex.find(item)
                        val header = headerMatch?.groups?.get(1)?.value
                        val subHeader = subHeaderMatch?.groups?.get(1)?.value
                        if (header != null && subHeader != null) {
                            results.add(SearchResult(header, subHeader))
                        }
                    }
                }
                SimpleResult.Success(results)
            }
        } catch (e: Exception) {
            e.simpleError()
        }
    }
}
