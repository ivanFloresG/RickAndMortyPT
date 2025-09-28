package com.aion.rickandmortypt.features.favoriteList.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aion.rickandmortypt.core.network.Result
import com.aion.rickandmortypt.features.characterDetails.domain.models.Character
import com.aion.rickandmortypt.features.characterList.domain.use_case.CharacterListUseCase
import com.aion.rickandmortypt.features.characterList.ui.CharacterListViewModelUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val characterListUseCase: CharacterListUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(FavoriteListUiState())
    val state: StateFlow<FavoriteListUiState> = _state


    fun onRefresh() {
        _state.update {
            it.copy(
                page = 1,
                isRefreshing = true,
            )
        }
        fetchPage(page = 1, append = false)
    }

    fun loadMore() {
        val s = _state.value
        if (s.isLoading || s.isRefreshing) return

        val pageToLoad = s.page + 1
        if (pageToLoad > s.totalPages) return

        _state.update { it.copy(isLoading = true) }
        fetchPage(page = pageToLoad, append = true)
    }

    fun fetchPage(page: Int, append: Boolean) {
        viewModelScope.launch {
            characterListUseCase.getFavoriteList(page = page, favorite = true).onEach { result ->
                when (result) {
                    is Result.Succes -> {
                        _state.update { prev ->
                            val incomingList: List<Character> = result.data?.characterList ?: emptyList()
                            val newList: List<Character> =
                                if (append) prev.items + incomingList
                                else incomingList

                            prev.copy(
                                items = newList,
                                page = page,
                                isLoading = false,
                                isRefreshing = false,
                                totalPages = result.data?.pages ?: 0
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