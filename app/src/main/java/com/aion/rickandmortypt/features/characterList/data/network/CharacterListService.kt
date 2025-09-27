package com.aion.rickandmortypt.features.characterList.data.network

import com.aion.rickandmortypt.core.network.ApiClient
import com.aion.rickandmortypt.features.characterList.data.network.response.CharacterListDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class CharacterListService @Inject constructor(
    private val apiClient: ApiClient
) {
    suspend fun getCharacterList(page: Int, name: String? = null, state: String? = null, spice: String? = null): Response<CharacterListDTO>{
        return withContext(Dispatchers.IO){
            apiClient.getCharacterList(page, name, state, spice)
        }
    }
}