package com.aion.rickandmortypt.features.characterDetails.data.network

import com.aion.rickandmortypt.core.network.ApiClient
import com.aion.rickandmortypt.features.characterList.data.network.response.CharacterDTO
import com.aion.rickandmortypt.features.characterList.data.network.response.EpisodeDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class CharacterService @Inject constructor(
    private val apiClient: ApiClient
) {
    suspend fun getCharacter(id: Int): Response<CharacterDTO> {
        return withContext(Dispatchers.IO) {
            apiClient.getCharacter(id)
        }
    }

    suspend fun getMultipleEpisodes(episodes: String): Response<List<EpisodeDTO>> {
        return withContext(Dispatchers.IO) {
            apiClient.getMultipleEpisodes(episodes)
        }
    }
}