package com.aion.rickandmortypt.features.characterDetails.ui

sealed interface CharacterViewModelUiEvent {
    data class NavigateToDetail(val id: Int) : CharacterViewModelUiEvent
    object ScrollToTop: CharacterViewModelUiEvent
    data class ShowSnackBar(val message: String): CharacterViewModelUiEvent
}