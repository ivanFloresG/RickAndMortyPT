package com.aion.rickandmortypt.features.characterList.ui

sealed interface CharacterListViewModelUiEvent {
    data class NavigateToDetail(val id: Int) : CharacterListViewModelUiEvent
    object ScrollToTop: CharacterListViewModelUiEvent
    data class ShowSnackBar(val message: String): CharacterListViewModelUiEvent
}