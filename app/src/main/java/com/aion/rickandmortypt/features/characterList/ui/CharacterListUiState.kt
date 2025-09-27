package com.aion.rickandmortypt.features.characterList.ui

import com.aion.rickandmortypt.features.characterList.domain.models.Character

data class CharacterListUiState(

    val filterName: String = "",
    val filterState: String = "",
    val filterSpice: String = "",

    val appliedName: String = "",
    val appliedState: String = "",
    val appliedSpice: String = "",

    val items: List<Character> = listOf(),
    val selected: Int? = null,

    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,

    val page: Int = 1,
    val totalPages: Int = 0
)