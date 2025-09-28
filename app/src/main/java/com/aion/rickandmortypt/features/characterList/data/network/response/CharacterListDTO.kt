package com.aion.rickandmortypt.features.characterList.data.network.response

import com.aion.rickandmortypt.features.characterDetails.data.network.response.Info
import com.aion.rickandmortypt.features.characterDetails.domain.models.Character
import com.aion.rickandmortypt.features.characterList.domain.models.CharacterListInfo
import com.google.gson.annotations.SerializedName

data class CharacterListDTO(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<CharacterDTO>
)

fun CharacterListDTO.toList(): CharacterListInfo {
    val list = results.mapIndexed { x, result ->
            Character(
                id = result.id,
                name = result.name,
                status = result.status,
                species = result.species,
                type = result. type,
                gender = result.gender,
                origin = result.origin,
                location = result.location,
                image = result.image,
                episodes = result.episode,
                url = result.url,
                created = result.created,
                favorite = false
            )
    }

    return CharacterListInfo(pages = info.pages, characterList = list)
}