package com.aion.rickandmortypt.features.characterList.domain.models

data class CharacterListInfo(
    val count: Int,
    val pages: Int,
    val characterList: List<Character>
)
