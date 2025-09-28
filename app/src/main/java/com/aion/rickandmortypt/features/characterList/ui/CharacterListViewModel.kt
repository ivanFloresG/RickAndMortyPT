package com.aion.rickandmortypt.features.characterList.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aion.rickandmortypt.core.network.Result
import com.aion.rickandmortypt.features.characterDetails.domain.models.Character
import com.aion.rickandmortypt.features.characterList.domain.use_case.CharacterListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterListUseCase: CharacterListUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterListUiState())
    val state: StateFlow<CharacterListUiState> = _state

    private val _events = MutableSharedFlow<CharacterListViewModelUiEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<CharacterListViewModelUiEvent> = _events

    fun onFilterNameChanged(text: String) {
        _state.update { it.copy(filterName = text) }
    }

    fun onFilterState(text: String) {
        _state.update {
            it.copy(
                filterState = text,
                selectedAlive = text.equals("alive"),
                selectedDeath = text.equals("dead"),
                selectedUnknown = text.equals("unknown"),
            )
        }
    }

    fun onFilterSpice(text: String) {
        _state.update {
            it.copy(filterSpice = text)
        }
    }

    fun onSearchClicked(isSearch: Boolean) {
        _state.update { it.copy(isSearching = isSearch) }
    }

    fun onItemClicked(id: Int) {
        _state.update { it.copy(selected = id) }
    }

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
        println(pageToLoad)
        println(s.totalPages)
        if (pageToLoad > s.totalPages) return

        _state.update { it.copy(isLoading = true) }
        fetchPage(page = pageToLoad, append = true)
    }

    fun onClearFilters() {
        _state.update {
            it.copy(
                filterName = "",
                filterSpice = "",
                filterState = "",
                appliedName = null,
                appliedState = null,
                appliedSpice = null,
                selectedAlive = false,
                selectedDeath = false,
                selectedUnknown = false,
                page = 1,
                isRefreshing = true,
                items = emptyList()
            )
        }

        _events.tryEmit(CharacterListViewModelUiEvent.ScrollToTop)
        fetchPage(page = 1, append = false)
    }

    fun onApplyFilters() {
        val appliedName = _state.value.filterName.trim().ifBlank { null }
        val appliedState = _state.value.filterState.trim().ifBlank { null }
        val appliedSpice = _state.value.filterSpice.trim().ifBlank { null }

        _state.update {
            it.copy(
                appliedName = appliedName,
                appliedState = appliedState,
                appliedSpice = appliedSpice,
                page = 1,
                isRefreshing = true,
                items = emptyList()
            )
        }

        _events.tryEmit(CharacterListViewModelUiEvent.ScrollToTop)
        fetchPage(page = 1, append = false, name = appliedName, state = appliedState, spice = appliedSpice)
    }

    fun fetchPage(page: Int, append: Boolean, name: String? = null, state: String? = null, spice: String? = null) {
        viewModelScope.launch {
            characterListUseCase.invoke(page, name, state, spice).onEach { result ->
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
                        _events.tryEmit(CharacterListViewModelUiEvent.ShowSnackBar(result.message ?: "Uknown error"))
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