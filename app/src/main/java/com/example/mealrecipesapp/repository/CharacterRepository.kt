package com.example.mealrecipesapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.mealrecipesapp.data.Character
import com.example.mealrecipesapp.data.CharacterDao
import com.example.mealrecipesapp.data.CharacterDatabase
import com.example.mealrecipesapp.network.CharacterApi
import com.example.mealrecipesapp.network.RetrofitClient
import retrofit2.Response

/**
 * Handles local (Room) and remote (Retrofit) data operations.
 */
class CharacterRepository private constructor(
    context: Context
) {
    private val dao: CharacterDao =
        CharacterDatabase.getInstance(context).characterDao()
    private val api: CharacterApi = RetrofitClient.characterApi

    /** All saved favorites from Room */
    fun getAllFavorites(): LiveData<List<Character>> =
        dao.getAllFavorites()

    /** Lookup a favorite by ID */
    fun findFavoriteById(id: Int): Character? =
        dao.findFavoriteById(id)

    /** Insert or update a favorite */
    suspend fun insertFavorite(character: Character) {
        dao.insertFavorite(character)
    }

    /** Retrofit: fetch a single character by its ID (returns Response<Character>) */
    suspend fun fetchCharacterById(id: String): Response<Character> =
        api.getCharacterById(id.toInt())

    /** Retrofit: search characters by name (returns a wrapper with .results) */
    suspend fun searchCharacters(name: String) =
        api.searchCharacters(name)

    companion object {
        @Volatile private var instance: CharacterRepository? = null

        fun getInstance(context: Context): CharacterRepository =
            instance ?: synchronized(this) {
                instance ?: CharacterRepository(context)
                    .also { instance = it }
            }
    }
}
