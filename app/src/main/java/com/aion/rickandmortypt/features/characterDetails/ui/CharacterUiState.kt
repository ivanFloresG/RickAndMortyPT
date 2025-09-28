package com.aion.rickandmortypt.features.characterDetails.ui
import com.aion.rickandmortypt.features.characterDetails.data.network.response.Location
import com.aion.rickandmortypt.features.characterDetails.data.network.response.Origin
import com.aion.rickandmortypt.features.characterDetails.domain.models.Character

data class CharacterUiState(
    val item: Character = Character(
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
        ""
        ),

    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
)