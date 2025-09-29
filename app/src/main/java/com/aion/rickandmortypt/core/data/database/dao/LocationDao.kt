package com.aion.rickandmortypt.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aion.rickandmortypt.core.data.database.entities.CharacterEntity
import com.aion.rickandmortypt.core.data.database.entities.LocationEntity

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(characters: List<LocationEntity>)

    @Query("SELECT * FROM location WHERE id = :id")
    suspend fun getLocationById(id: Int): LocationEntity?
}