package com.aion.rickandmortypt.features.characterDetails.domain.models

import com.aion.rickandmortypt.core.data.database.entities.EpisodeEntity

data class Episode (
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val url: String,
    val created: String,
    val watched: Boolean
)

fun Episode.toEntity() = EpisodeEntity(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode,
    url = url,
    created = created,
    watched = false
)