package com.aion.rickandmortypt.features.characterList.data.network.response

import com.aion.rickandmortypt.features.characterDetails.domain.models.Episode
import com.google.gson.annotations.SerializedName

data class EpisodeDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("created")
    val created: String
)

fun EpisodeDTO.toEpisode() = Episode(
        id = id,
        name = name,
        airDate = airDate,
        episode = episode,
        url = url,
        created = created,
        watched = false
    )