package com.aion.rickandmortypt.features.characterDetails.data

import com.aion.rickandmortypt.core.network.Result
import com.aion.rickandmortypt.features.characterDetails.domain.models.Character
import com.aion.rickandmortypt.features.characterList.domain.models.CharacterListInfo
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacterFromApi(id: Int): Flow<Result<Character>>
    suspend fun getCharacterFromDb(id: Int): Flow<Result<Character>>
    suspend fun saveCharacterToDb(item: Character)
}