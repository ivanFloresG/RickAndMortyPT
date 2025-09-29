package com.aion.rickandmortypt.features.characterDetails.domain.use_case

import com.aion.rickandmortypt.core.network.Result
import com.aion.rickandmortypt.features.characterDetails.data.CharacterRepositoryImp
import com.aion.rickandmortypt.features.characterDetails.domain.models.Character
import com.aion.rickandmortypt.features.characterDetails.domain.models.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import javax.inject.Inject

class CharacterUseCase @Inject constructor(
    private val repository: CharacterRepositoryImp
) {
    suspend fun invoke(
        id: Int,
    ): Flow<Result<Character>> = flow {
        try {
            val apiRes = repository.getCharacterFromApi(id).first{it !is Result.Loading}
            when (apiRes) {
                is Result.Succes -> {
                    val res = apiRes.data
                    if(res != null){
                        repository.saveCharacterToDb(res)
                    }
                }
                is Result.Error -> {
                }
                is Result.Loading -> {
                }
            }

            emitAll(repository.getCharacterFromDb(id)
                .filterNot { it is Result.Loading })

        } catch (e: IOException){
        }
    }.flowOn(Dispatchers.IO) as Flow<Result<Character>>

    suspend fun loadEpisodes(
        episodes: String,
    ): Flow<Result<List<Episode>>> = flow {
        try {
            val apiRes = repository.getEpisodesFromApi(episodes).first{it !is Result.Loading}
            when (apiRes) {
                is Result.Succes -> {
                    val res = apiRes.data
                    if(res != null){
                        repository.saveEpisodesToDb(res)
                    }
                }
                is Result.Error -> {
                }
                is Result.Loading -> {
                }
            }

            emitAll(repository.getEpisodesFromDb(episodes)
                .filterNot { it is Result.Loading })

        } catch (e: IOException){
        }
    }.flowOn(Dispatchers.IO) as Flow<Result<List<Episode>>>


    suspend fun updateFavoriteStatus(id: Int, status: Boolean){
       repository.updateFavoriteStatus(id, status)
    }

    suspend fun updateEpisodeWatched(id: Int, watched: Boolean){
        repository.updateEpisodeWatched(id, watched)
    }
}