package com.example.rickopedia.repository

import android.content.Context
import com.example.rickopedia.data.Character
import com.example.rickopedia.data.CharacterDao
import com.example.rickopedia.data.CharacterDatabase
import com.example.rickopedia.network.CharacterApi
import com.example.rickopedia.network.RetrofitClient
import androidx.lifecycle.LiveData
import retrofit2.Response

class CharacterRepository private constructor(
    context: Context
) {
    private val dao: CharacterDao =
        CharacterDatabase.getInstance(context).characterDao()
    private val api: CharacterApi = RetrofitClient.characterApi

    /** All saved favorites */
    fun getAllFavorites(): LiveData<List<Character>> =
        dao.getAllFavorites()

    /** One saved favorite by ID */
    fun findFavoriteById(id: Int): Character? =
        dao.findFavoriteById(id)

    /** Insert / update favorite */
    suspend fun insertFavorite(character: Character) {
        dao.insertFavorite(character)
    }

    /** Fetch a single character from network */
    suspend fun fetchCharacterById(id: Int): Response<Character> =
        api.getCharacterById(id)

    /**
     * Search **every** page of the API for `name` and merge all results.
     * Returns a simple List<Character>.
     */
    suspend fun searchCharacters(name: String): List<Character> {
        val accumulated = mutableListOf<Character>()

        // page 1
        val firstResp = api.searchCharacters(name, page = 1)
        if (!firstResp.isSuccessful) return emptyList()
        val firstBody = firstResp.body() ?: return emptyList()

        // add page-1 results
        accumulated += (firstBody.results ?: emptyList())

        // how many pages do we have?
        val totalPages = firstBody.info?.pages ?: 1

        // fetch pages 2..N
        for (page in 2..totalPages) {
            val resp = api.searchCharacters(name, page)
            if (resp.isSuccessful) {
                resp.body()?.results?.let { accumulated += it }
            } else {
                // optionally break on error
                break
            }
        }

        return accumulated
    }

    companion object {
        @Volatile private var instance: CharacterRepository? = null

        fun getInstance(context: Context): CharacterRepository =
            instance ?: synchronized(this) {
                instance ?: CharacterRepository(context)
                    .also { instance = it }
            }
    }
}
