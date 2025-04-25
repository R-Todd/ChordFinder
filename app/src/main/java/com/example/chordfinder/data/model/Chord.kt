// File: data/model/Chord.kt
// Functionality: Data model representing a chord with details such as name, chord notes, and fingering.

package com.example.chordfinder.data.model

data class Chord(
    val chordName: String,
    val chordNotes: String,
    val fingering: String
)