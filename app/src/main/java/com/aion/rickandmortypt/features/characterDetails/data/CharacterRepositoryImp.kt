package com.aion.rickandmortypt.features.characterDetails.data

import com.aion.rickandmortypt.core.data.database.dao.CharacterDao
import com.aion.rickandmortypt.core.data.database.dao.EpisodeDao
import com.aion.rickandmortypt.core.data.database.entities.CharacterEntity
import com.aion.rickandmortypt.core.data.database.entities.toCharacter
import com.aion.rickandmortypt.core.data.database.entities.toEpisode
import com.aion.rickandmortypt.core.network.Result
import com.aion.rickandmortypt.features.characterDetails.data.network.CharacterService
import com.aion.rickandmortypt.features.characterDetails.domain.models.Character
import com.aion.rickandmortypt.features.characterDetails.domain.models.Episode
import com.aion.rickandmortypt.features.characterDetails.domain.models.toEntity
import com.aion.rickandmortypt.features.characterList.data.network.response.toCharacter
import com.aion.rickandmortypt.features.characterList.data.network.response.toEpisode
import com.aion.rickandmortypt.features.characterList.domain.models.CharacterListInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class CharacterRepositoryImp @Inject constructor(
    private val service: CharacterService,
    private val characterDao: CharacterDao,
    private val episodeDao: EpisodeDao
) : CharacterRepository {


    override suspend fun getCharacterFromApi(id: Int): Flow<Result<Character>> = flow {
        emit(Result.Loading())
        try {
            val response = service.getCharacter(id).body()?.toCharacter()
            emit(Result.Succes(response))
        } catch (e: HttpException) {
            emit(
                Result.Error(
                    message = "Error",
                    data = null
                )
            )

        } catch (e: IOException) {
            emit(
                Result.Error(
                    message = "Unable to resolve server",
                    data = null
                )
            )
        }
    }

    override suspend fun getCharacterFromDb(
        id: Int,
    ): Flow<Result<Character>> = flow {
        emit(Result.Loading())
        try {
            val response: CharacterEntity = characterDao.getCharacterById(id)
            emit(Result.Succes(response.toCharacter()))
        } catch (e: HttpException) {
            emit(
                Result.Error(
                    message = "Error",
                    data = null
                )
            )

        } catch (e: IOException) {
            emit(
                Result.Error(
                    message = "Unable to resolve server",
                    data = null
                )
            )

        }
    }

    override suspend fun getEpisodesFromApi(episodes: String): Flow<Result<List<Episode>>> = flow {
        emit(Result.Loading())
        try {
            val response: List<Episode> = service.getMultipleEpisodes(episodes).body()?.map { it.toEpisode() } ?: emptyList()
            emit(Result.Succes(response))
        } catch (e: HttpException) {
            emit(
                Result.Error(
                    message = "Error",
                    data = null
                )
            )

        } catch (e: IOException) {
            emit(
                Result.Error(
                    message = "Unable to resolve server",
                    data = null
                )
            )
        }
    }

    override suspend fun getEpisodesFromDb(
        episodes: String,
    ): Flow<Result<List<Episode>>> = flow {
        emit(Result.Loading())
        try {
            val ep = episodes.split(",")
            val epLst: List<Episode> = episodeDao.getAllEpisodes().map { it.toEpisode() }
            val response = epLst.filter { ep.contains(it.id.toString()) }

            emit(Result.Succes(response))
        } catch (e: HttpException) {
            emit(
                Result.Error(
                    message = "Error",
                    data = null
                )
            )

        } catch (e: IOException) {
            emit(
                Result.Error(
                    message = "Unable to resolve server",
                    data = null
                )
            )
        }
    }


    override suspend fun saveEpisodesToDb(items: List<Episode>){
        episodeDao.insertOrUpdateEpisode(items.map { it.toEntity() })
    }

    override suspend fun saveCharacterToDb(item: Character) {
        characterDao.insertCharacter(item.toEntity())
    }

    override suspend fun updateFavoriteStatus(id: Int, favorite: Boolean) {
        characterDao.updateFavorite(id, favorite)
    }

    override suspend fun updateEpisodeWatched(id: Int, watched: Boolean) {
        episodeDao.updateEpisodeWatched(id, watched)
    }
}

