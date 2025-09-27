package com.aion.rickandmortypt.features.characterList.domain.use_case

import com.aion.rickandmortypt.core.network.Result
import com.aion.rickandmortypt.features.characterList.data.CharacterListRepository
import com.aion.rickandmortypt.features.characterList.data.CharacterListRepositoryImp
import com.aion.rickandmortypt.features.characterList.domain.models.CharacterListInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterListUseCase @Inject constructor(
    private val repository: CharacterListRepositoryImp
) {
    suspend fun invoke(page: Int): Flow<Result<CharacterListInfo>> {
        return repository.getCharacterList(page)
    }
}