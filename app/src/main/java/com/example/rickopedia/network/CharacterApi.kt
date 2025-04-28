package com.example.rickopedia.network

import com.example.rickopedia.data.CharacterResponse
import com.example.rickopedia.data.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {
    @GET("character")
    suspend fun searchCharacters(
        @Query("name") name: String
    ): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Response<Character>
}
