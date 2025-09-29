package com.aion.rickandmortypt.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aion.rickandmortypt.core.data.database.entities.CharacterEntity

@Dao
interface CharacterDao {
    @Query(
        "SELECT * FROM character " +
                "WHERE (:name IS NULL OR :name = '' OR name LIKE '%' || :name ||  '%' COLLATE NOCASE ) " +
                "AND (:state IS NULL OR :state = '' OR status like '%' || :state ||  '%' COLLATE NOCASE) " +
                "AND (:spice IS NULL OR :spice = '' OR species like '%' || :spice ||  '%' COLLATE NOCASE) " +
                "AND (:favorite IS NULL OR favorite = :favorite) " +
                "ORDER BY id ASC " +
                "LIMIT 20 " +
                "OFFSET ((:page-1) * 20)"
    )
    suspend fun getAllCharacters(page: Int, name: String?, state: String?, spice: String?, favorite: Boolean?): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterOnly(characters: CharacterEntity)

    suspend fun insertCharacter(character: CharacterEntity) {
        val existingCharacter = getCharacterById(character.id)
        if (existingCharacter != null) {
            val updateCharacter = character.copy(
                favorite = existingCharacter.favorite
            )
            updateCharacter(updateCharacter)
        } else {
            insertCharacter(character)
        }
    }

    @Query("SELECT * FROM character WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity

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
            } else {
                insertCharacterOnly(newCharacter)
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