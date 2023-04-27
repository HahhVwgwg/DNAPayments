package com.dnapayments.data.api_clients

import com.dnapayments.data.model.Character
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ICharacterDetailsService {

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("api/characters/{id}")
    fun getCharacterDetailedAsync(
        @Path(value = "id",
            encoded = true) charId: Int,
    ): Deferred<List<Character>>

}