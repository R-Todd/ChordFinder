package com.example.rickopedia.network

import com.example.rickopedia.data.CharacterResponse
import com.example.rickopedia.data.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {
    /**
     * Search by name, page-by-page.
     * Rick & Morty API uses ?page=1,2,…
     */
    @GET("character")
    suspend fun searchCharacters(
        @Query("name") name: String,
        @Query("page") page: Int = 1
    ): Response<CharacterResponse>

    /** Get one character by ID */
    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Response<Character>
}
