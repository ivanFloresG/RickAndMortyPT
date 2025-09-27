package com.aion.rickandmortypt.features.characterList.data

import com.aion.rickandmortypt.core.network.Result
import com.aion.rickandmortypt.features.characterList.domain.models.CharacterListInfo
import kotlinx.coroutines.flow.Flow

interface CharacterListRepository {
    suspend fun getCharacterList(page: Int, name: String? = null, state: String? = null, spice: String? = null): Flow<Result<CharacterListInfo>>
}