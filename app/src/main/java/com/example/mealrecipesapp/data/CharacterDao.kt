package com.example.mealrecipesapp.data // Update with your actual package name

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.lifecycle.LiveData

@Dao
interface CharacterDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): LiveData<List<Character>>

    @Query("SELECT * FROM favorites WHERE id = :id")
    fun findFavoriteById(id: Int): Character?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(character: Character)
}
