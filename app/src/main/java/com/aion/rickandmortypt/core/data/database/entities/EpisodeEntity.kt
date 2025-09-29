package com.aion.rickandmortypt.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aion.rickandmortypt.features.characterDetails.domain.models.Episode

@Entity(tableName = "episode")
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "airDate") val airDate: String,
    @ColumnInfo(name = "episode") val episode: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "created") val created: String,
    @ColumnInfo(name = "watched") val watched: Boolean
)

fun EpisodeEntity.toEpisode() = Episode(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode,
    url = url,
    created = created,
    watched = watched
)