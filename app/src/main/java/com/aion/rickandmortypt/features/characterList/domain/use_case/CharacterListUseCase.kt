package com.aion.rickandmortypt.features.characterList.domain.use_case

import com.aion.rickandmortypt.core.network.Result
import com.aion.rickandmortypt.features.characterList.data.CharacterListRepositoryImp
import com.aion.rickandmortypt.features.characterList.domain.models.CharacterListInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import javax.inject.Inject

class CharacterListUseCase @Inject constructor(
    private val repository: CharacterListRepositoryImp
) {
    suspend fun invoke(
        page: Int,
        name: String? = null,
        state: String? = null,
        spice: String? = null
    ): Flow<Result<CharacterListInfo>> = flow {
        try {
            val apiRes = repository.getCharacterListFromApi(page, name, state, spice).first{it !is Result.Loading}
            when (apiRes) {
                is Result.Succes -> {
                    val list = apiRes.data?.characterList.orEmpty()
                    if(list.isNotEmpty()){
                        repository.saveCharactersToDb(list)
                    }
                }
                is Result.Error -> {
                }
                is Result.Loading -> {
                }
            }

            emitAll(repository.getCharacterListFromDb(page,name,state,spice)
                .filterNot { it is Result.Loading })

        } catch (e: IOException){
        }
    }.flowOn(Dispatchers.IO) as Flow<Result<CharacterListInfo>>
}