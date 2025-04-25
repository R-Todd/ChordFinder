// File: data/api/UberchordService
// Functionality: searches and GET's information from UberChord API

package com.example.chordfinder.data.api


// import chord.kt to search individually chords by name
import com.example.chordfinder.data.model.Chord
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface UberchordService {
    // get request to find chord by name,
    @GET("v1/chords")
    suspend fun getChords(@Query("name") chordName: String): Response<List<Chord>>
}