package com.example.mealrecipesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealrecipesapp.repository.CharacterRepository

/**
 * Simple ViewModelProvider.Factory so we can pass our singleton
 * CharacterRepository into CharacterViewModel.
 */
class CharacterViewModelFactory(
    private val repo: CharacterRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
