package com.aion.rickandmortypt.features.favoriteList.ui

import com.aion.rickandmortypt.features.characterDetails.domain.models.Character

data class FavoriteListUiState(
    val items: List<Character> = listOf(),
    val selected: Int? = null,

    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,

    val page: Int = 1,
    val totalPages: Int = 0
)