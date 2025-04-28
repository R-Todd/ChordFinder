package com.example.rickopedia.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded

@Entity(tableName = "favorites")
data class Character(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    @Embedded(prefix = "origin_") val origin: LocationRef,
    @Embedded(prefix = "location_") val location: LocationRef,
    val image: String
)

data class LocationRef(
    val name: String,
    val url: String
)
