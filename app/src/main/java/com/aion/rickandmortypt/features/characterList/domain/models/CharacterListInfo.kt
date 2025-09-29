package com.aion.rickandmortypt.features.characterList.domain.models

import com.aion.rickandmortypt.features.characterDetails.domain.models.Character

data class CharacterListInfo(
    val pages: Int,
    val characterList: List<Character>
)
