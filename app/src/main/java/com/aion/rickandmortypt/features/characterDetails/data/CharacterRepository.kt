package com.aion.rickandmortypt.features.characterDetails.data

import com.aion.rickandmortypt.core.network.Result
import com.aion.rickandmortypt.features.characterDetails.domain.models.Character
import com.aion.rickandmortypt.features.characterDetails.domain.models.Episode
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacterFromApi(id: Int): Flow<Result<Character>>
    suspend fun getCharacterFromDb(id: Int): Flow<Result<Character>>
    suspend fun getEpisodesFromApi(episodes: String): Flow<Result<List<Episode>>>
    suspend fun getEpisodesFromDb(episodes: String): Flow<Result<List<Episode>>>
    suspend fun saveEpisodesToDb(items: List<Episode>)
    suspend fun saveCharacterToDb(item: Character)
    suspend fun updateFavoriteStatus(id: Int, favorite: Boolean)
    suspend fun updateEpisodeWatched(id: Int, watched: Boolean)
}