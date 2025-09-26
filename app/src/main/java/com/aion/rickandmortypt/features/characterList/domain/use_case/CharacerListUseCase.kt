package com.aion.rickandmortypt.features.characterList.domain.use_case

import com.aion.rickandmortypt.core.network.Result
import com.aion.rickandmortypt.features.characterList.data.CharacterListRepository
import com.aion.rickandmortypt.features.characterList.domain.models.Character
import com.aion.rickandmortypt.features.characterList.domain.models.CharacterListInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacerListUseCase @Inject constructor(
    private val repository: CharacterListRepository
){
    suspend fun invoke(page: Int): Flow<Result<CharacterListInfo>>{
        return repository.getCharacterList(page)
    }
}