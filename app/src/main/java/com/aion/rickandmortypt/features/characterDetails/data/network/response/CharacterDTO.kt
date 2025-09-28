package com.aion.rickandmortypt.features.characterList.data.network.response

import com.aion.rickandmortypt.features.characterDetails.data.network.response.Location
import com.aion.rickandmortypt.features.characterDetails.data.network.response.Origin
import com.aion.rickandmortypt.features.characterDetails.domain.models.Character
import com.google.gson.annotations.SerializedName
import kotlin.String
import kotlin.collections.List

data class CharacterDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("origin")
    val origin: Origin,
    @SerializedName("location")
    val location: Location,
    @SerializedName("image")
    val image: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("url")
    val url: String,
    @SerializedName("created")
    val created: String
)

fun CharacterDTO.toCharacter(): Character {
    return Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type =  type,
        gender = gender,
        origin = origin,
        location = location,
        image = image,
        episodes = episode,
        url = url,
        created = created,
        favorite = false
    )
}