package com.aion.rickandmortypt.core.data.database.dao

import androidx.compose.runtime.sourceInformation
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aion.rickandmortypt.core.data.database.entities.CharacterEntity
import com.aion.rickandmortypt.core.data.database.entities.EpisodeEntity

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<EpisodeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episode: EpisodeEntity)


    @Query("SELECT * FROM episode WHERE id = :id")
    suspend fun getEpisodeById(id: Int): EpisodeEntity?

    @Query("SELECT * FROM episode")
    suspend fun getAllEpisodes(): List<EpisodeEntity>

    @Update
    suspend fun updateEpisode(episode: EpisodeEntity)

    suspend fun insertOrUpdateEpisode(episodes: List<EpisodeEntity>){
        episodes.forEach{ newEpisode ->
            val existingEpisode = getEpisodeById(newEpisode.id)
            if(existingEpisode != null){
                val updateCharacter = newEpisode.copy(
                    watched = existingEpisode.watched
                )
                updateEpisode(updateCharacter)
            } else {
                insertEpisode(newEpisode)
            }
        }
    }

    suspend fun updateEpisodeWatched(idEpisode: Int, watched: Boolean) {
        val existingEpisode = getEpisodeById(idEpisode)
        if (existingEpisode != null) {
            val updateEpisode = existingEpisode.copy(
                watched = watched
            )
            updateEpisode(updateEpisode)
        }
    }
}