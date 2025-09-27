package com.aion.rickandmortypt.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aion.rickandmortypt.core.data.database.entities.CharacterEntity

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<CharacterEntity>)

    @Query("SELECT * FROM character WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity?

    @Update
    suspend fun updateCharacter(character: CharacterEntity)

    suspend fun insertOrUpdateCharacters(characters: List<CharacterEntity>) {
        characters.forEach { newCharacter ->
            val existingCharacter = getCharacterById(newCharacter.id)
            if (existingCharacter != null) {
                val updateCharacter = newCharacter.copy(
                    favorite = existingCharacter.favorite
                )

                updateCharacter(updateCharacter)
            }
        }
    }

    suspend fun updateFavorite(idCharacter: Int, fav: Boolean) {
        val existingCharacter = getCharacterById(idCharacter)

        if (existingCharacter != null) {
            val updateCharacter = existingCharacter.copy(
                favorite = fav
            )

            updateCharacter(updateCharacter)
        }
    }
}