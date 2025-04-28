package com.example.mealrecipesapp.viewmodel

import androidx.lifecycle.*
import com.example.mealrecipesapp.data.Character
import com.example.mealrecipesapp.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Shared ViewModel for searching, fetching, and favorites.
 */
class CharacterViewModel(
    private val repo: CharacterRepository
) : ViewModel() {

    /** Remember last search so we can restore the EditText on return */
    var lastQuery: String = ""

    /** Backing LiveData for name‐search results */
    private val _searchResults = MutableLiveData<List<Character>>()
    val searchResults: LiveData<List<Character>> = _searchResults

    /** LiveData list of favorites from Room */
    val favorites: LiveData<List<Character>> = repo.getAllFavorites()

    /**
     * Search characters by name, post results into [_searchResults].
     */
    fun searchCharacters(query: String) {
        lastQuery = query
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repo.searchCharacters(query)
            }
            // CharacterResponse.results is List<Character>?
            val list = response.results ?: emptyList()
            _searchResults.postValue(list)
        }
    }

    /**
     * Fetch a single character by ID, trying DB first then network.
     * Emits `Character?`.
     */
    fun getCharacterById(id: String): LiveData<Character?> = liveData(Dispatchers.IO) {
        // 1) Try Room
        repo.findFavoriteById(id.toInt())?.let {
            emit(it)
            return@liveData
        }
        // 2) Fallback to network
        val response: Response<Character> = repo.fetchCharacterById(id)
        if (response.isSuccessful) {
            emit(response.body())
        } else {
            emit(null)
        }
    }

    /** Insert into favorites table */
    fun insertFavorite(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertFavorite(character)
        }
    }
}

