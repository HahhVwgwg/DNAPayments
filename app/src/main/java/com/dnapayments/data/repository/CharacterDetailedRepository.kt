package com.dnapayments.data.repository

import com.dnapayments.R
import com.dnapayments.api_clients.ICharacterDetailsService
import com.dnapayments.data.Resource
import com.dnapayments.data.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterDetailedRepository(private val service: ICharacterDetailsService) {
    suspend fun fetchCharacterById(id: Int):
            Resource<List<Character>> {
        return try {
            withContext(Dispatchers.IO) {
                val result = service.getCharacterDetailedAsync(
                    id
                ).await()
                Resource.success(result)
            }
        } catch (e: Exception) {
            Resource.error(R.string.something_went_wrong)
        }
    }
}
