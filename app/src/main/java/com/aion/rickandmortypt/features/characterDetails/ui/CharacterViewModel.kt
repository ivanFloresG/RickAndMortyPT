package com.aion.rickandmortypt.features.characterDetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aion.rickandmortypt.core.network.Result
import com.aion.rickandmortypt.features.characterDetails.data.network.response.Location
import com.aion.rickandmortypt.features.characterDetails.data.network.response.Origin
import com.aion.rickandmortypt.features.characterDetails.domain.models.Character
import com.aion.rickandmortypt.features.characterDetails.domain.use_case.CharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterUiState())
    val state: StateFlow<CharacterUiState> = _state

    fun clearData(){
        _state.update { prev ->
            prev.copy(
                item = Character(
                    0,
                    "",
                    "",
                    "",
                    "",
                    "",
                    Origin("", ""),
                    Location("", ""),
                    "",
                    emptyList(),
                    "",
                    "",
                    favorite = false
                ),
                isLoading = false,
                isRefreshing = false,
            )
        }
    }

    fun onFavoriteClicked(){
        _state.update { prev ->
            prev.copy(
                item = prev.item.copy(favorite = prev.item.favorite.not())
            )
        }

        viewModelScope.launch {
            characterUseCase.updateFavoriteStatus(_state.value.item.id, _state.value.item.favorite)
        }
    }

    fun fetchCharacter(id: Int) {
        viewModelScope.launch {
            characterUseCase.invoke(id).onEach { result ->
                when (result) {
                    is Result.Succes -> {
                        _state.update { prev ->
                            prev.copy(
                                item = result.data ?: Character(
                                    0,
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    Origin("", ""),
                                    Location("", ""),
                                    "",
                                    emptyList(),
                                    "",
                                    "",
                                    false
                                ),
                                isLoading = false,
                                isRefreshing = false,
                            )
                        }
                    }

                    is Result.Error -> {
                        _state.update { prev ->
                            prev.copy(
                                isLoading = false,
                            )
                        }
                    }

                    is Result.Loading -> {
                        _state.update { prev ->
                            prev.copy(
                                isLoading = true,
                            )
                        }
                    }
                }
            }.launchIn(this)

        }
    }

}