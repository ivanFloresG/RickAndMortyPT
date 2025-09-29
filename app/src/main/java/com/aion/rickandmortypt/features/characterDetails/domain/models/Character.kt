package com.aion.rickandmortypt.features.characterDetails.domain.models

import com.aion.rickandmortypt.core.data.database.entities.CharacterEntity
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
    val created: String,
    val favorite: Boolean
)

fun Character.toEntity() = CharacterEntity(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    location = location.name,
    origin = origin.name,
    episode = episodes.joinToString(",") { it.substringAfterLast("/") },
    image = image,
    url = url,
    created = created,
    favorite = favorite,
)
