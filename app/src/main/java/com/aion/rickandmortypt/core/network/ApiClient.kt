package com.aion.rickandmortypt.core.network

import com.aion.rickandmortypt.features.characterList.data.network.response.CharacterDTO
import com.aion.rickandmortypt.features.characterList.data.network.response.CharacterListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {
    @GET("/api/character")
    suspend fun getCharacterList(
        @Query("page") pageNumber: Int,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null
    ): Response<CharacterListDTO>

    @GET("/api/character/id")
    suspend fun getCharacter(@Path("id") id: Int): Response<CharacterDTO>

    @GET("/api/character")
    suspend fun getCharacterListFiltered(
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null
    ): Response<CharacterListDTO>

}