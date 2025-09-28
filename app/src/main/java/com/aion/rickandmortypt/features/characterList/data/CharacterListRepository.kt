package com.aion.rickandmortypt.features.characterList.data

import android.view.MenuItem
import com.aion.rickandmortypt.core.network.Result
import com.aion.rickandmortypt.features.characterDetails.domain.models.Character
import com.aion.rickandmortypt.features.characterList.domain.models.CharacterListInfo
import kotlinx.coroutines.flow.Flow

interface CharacterListRepository {
    suspend fun getCharacterListFromApi(page: Int, name: String? = null, state: String? = null, spice: String? = null): Flow<Result<CharacterListInfo>>
    suspend fun getCharacterListFromDb(page: Int, name: String? = null, state: String? = null, spice: String? = null): Flow<Result<CharacterListInfo>>
    suspend fun saveCharactersToDb(items: List<Character>)
}