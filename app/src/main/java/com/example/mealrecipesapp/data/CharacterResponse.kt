package com.example.mealrecipesapp.data

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    val info: Info,
    val results: List<Character>
)

data class Info(
    val count: Int,
    val pages: Int,
    @SerializedName("next") val nextUrl: String?,
    @SerializedName("prev") val prevUrl: String?
)
