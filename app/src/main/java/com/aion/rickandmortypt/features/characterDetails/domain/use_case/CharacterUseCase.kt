package com.aion.rickandmortypt.features.characterDetails.domain.use_case

import com.aion.rickandmortypt.core.network.Result
import com.aion.rickandmortypt.features.characterDetails.data.CharacterRepositoryImp
import com.aion.rickandmortypt.features.characterList.data.CharacterListRepositoryImp
import com.aion.rickandmortypt.features.characterList.domain.models.CharacterListInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterUseCase @Inject constructor(
    private val repository: CharacterRepositoryImp
) {
    suspend fun invoke(page: Int, name: String? = null, state: String? = null, spice: String? = null): Flow<Result<CharacterListInfo>> {
        return repository.getCharacterList(page, name, state, spice)
    }
}