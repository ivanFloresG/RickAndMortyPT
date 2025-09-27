package com.aion.rickandmortypt.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aion.rickandmortypt.core.data.database.dao.CharacterDao
import com.aion.rickandmortypt.core.data.database.dao.EpisodeDao
import com.aion.rickandmortypt.core.data.database.dao.LocationDao
import com.aion.rickandmortypt.core.data.database.entities.CharacterEntity
import com.aion.rickandmortypt.core.data.database.entities.EpisodeEntity
import com.aion.rickandmortypt.core.data.database.entities.LocationEntity


@Database(
    entities = [
        CharacterEntity::class,
        EpisodeEntity::class,
        LocationEntity::class
    ], version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDao
    abstract fun getEpisodeDao(): EpisodeDao
    abstract fun getLocationDao(): LocationDao
}