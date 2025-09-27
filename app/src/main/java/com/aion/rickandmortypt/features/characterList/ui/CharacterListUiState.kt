package com.aion.rickandmortypt.features.characterList.ui

import com.aion.rickandmortypt.features.characterList.domain.models.Character

data class CharacterListUiState(

    val filterName: String = "",
    val filterState: String = "",
    val filterSpice: String = "",

    val appliedName: String? = null,
    val appliedState: String? = null,
    val appliedSpice: String? = null,

    val selectedAlive: Boolean = false,
    val selectedDeath: Boolean = false,
    val selectedUnknown: Boolean = false,

    val isSearching: Boolean = false,

    val items: List<Character> = listOf(),
    val selected: Int? = null,

    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,

    val page: Int = 1,
    val totalPages: Int = 0
)