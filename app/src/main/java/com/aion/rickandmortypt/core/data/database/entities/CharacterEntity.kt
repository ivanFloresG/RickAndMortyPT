package com.aion.rickandmortypt.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "species") val species: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "idLocation") val idLocation: Int,
    @ColumnInfo(name = "idOrigin") val idOrigin: Int,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "created") val created: String,
    @ColumnInfo(name = "favorite") val favorite: Boolean
)