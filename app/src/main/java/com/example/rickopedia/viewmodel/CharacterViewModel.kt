package com.example.rickopedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickopedia.data.Character
import com.example.rickopedia.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repo: CharacterRepository
) : ViewModel() {

    // so we can restore the last search text
    var lastQuery: String = ""

    // exposes favorites from Room
    val favorites: LiveData<List<Character>> = repo.getAllFavorites()

    // for one‐off searches
    private val _searchResults = MutableLiveData<List<Character>>()
    val searchResults: LiveData<List<Character>> = _searchResults

    fun searchCharacters(query: String) {
        lastQuery = query
        viewModelScope.launch(Dispatchers.IO) {
            val all = repo.searchCharacters(query)  // returns List<Character>
            _searchResults.postValue(all)
        }
    }

    fun insertFavorite(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertFavorite(character)
        }
    }

    fun getCharacterById(id: Int, onResult: (Character?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.findFavoriteById(id)?.let {
                onResult(it)
            } ?: run {
                val resp = repo.fetchCharacterById(id)
                onResult(if (resp.isSuccessful) resp.body() else null)
            }
        }
    }
}
