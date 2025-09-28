package com.aion.rickandmortypt.features.characterList.data

import com.aion.rickandmortypt.core.data.database.dao.CharacterDao
import com.aion.rickandmortypt.core.data.database.entities.CharacterEntity
import com.aion.rickandmortypt.core.data.database.entities.toCharacter
import com.aion.rickandmortypt.core.network.Result
import com.aion.rickandmortypt.features.characterDetails.domain.models.Character
import com.aion.rickandmortypt.features.characterDetails.domain.models.toEntity
import com.aion.rickandmortypt.features.characterList.data.network.CharacterListService
import com.aion.rickandmortypt.features.characterList.data.network.response.toList
import com.aion.rickandmortypt.features.characterList.domain.models.CharacterListInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class CharacterListRepositoryImp @Inject constructor(
    private val service: CharacterListService,
    private val characterDao: CharacterDao
) : CharacterListRepository {

    /*
override suspend fun getCharacterList(page: Int): Flow<Result<CharacterListInfo>> {
    return try{
        var response = service.getCharacterList(page)
        if(response.isSuccessful){ }

    }
    }
     */
    override suspend fun getCharacterListFromApi(page: Int, name: String?, state: String?, spice: String?): Flow<Result<CharacterListInfo>> = flow {
        emit(Result.Loading())
        try {
            val response = service.getCharacterList(page, name, state, spice).body()?.toList()
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

    override suspend fun getCharacterListFromDb(
        page: Int,
        name: String?,
        state: String?,
        spice: String?
    ): Flow<Result<CharacterListInfo>> = flow {
        emit(Result.Loading())
        try {
            val response: List<CharacterEntity> = characterDao.getAllCharacters(page, name, state, spice)
            emit(Result.Succes(CharacterListInfo(pages = 42, response.map { it.toCharacter() })))
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

    override suspend fun saveCharactersToDb(items: List<Character>) {
        characterDao.insertOrUpdateCharacters(items.map { it.toEntity() })
    }
}