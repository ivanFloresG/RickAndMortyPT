package com.aion.rickandmortypt.features.characterDetails.domain.models

import com.aion.rickandmortypt.features.characterDetails.data.network.response.Location
import com.aion.rickandmortypt.features.characterDetails.data.network.response.Origin

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episodes: List<String>,
    val url: String,
    val created: String
)
