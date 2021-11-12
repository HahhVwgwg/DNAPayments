package com.dnapayments.data.repository

import com.dnapayments.R
import com.dnapayments.api_clients.ICharacterListService
import com.dnapayments.data.Resource
import com.dnapayments.data.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterRepository(private val service: ICharacterListService) {
    suspend fun fetchCharacters():
            Resource<List<Character>> {
        return try {
            withContext(Dispatchers.IO) {
                val result = service.getCharacterListAsync(
                ).await()
                Resource.success(result)
            }
        } catch (e: Exception) {
            Resource.error(
                R.string.something_went_wrong
            )
        }
    }
}
