package com.dnapayments.api_clients

import com.dnapayments.data.model.Character
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers

interface ICharacterListService {

    /*  CharacterList  */
    @Headers("Content-Type: application/json")
    @GET("api/characters")
    fun getCharacterListAsync(): Deferred<List<Character>>
}