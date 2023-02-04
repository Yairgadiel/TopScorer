package com.example.topscorer.ui.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topscorer.util.Resource
import com.example.topscorer.data.GamesRepository
import com.example.topscorer.data.model.GameList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(private val repository: GamesRepository) : ViewModel() {
    private val _games = MutableStateFlow<Resource<GameList>>(Resource.Loading())
    val games = _games.asStateFlow()

    init {
        getGames()
    }

    private fun getGames() {
        viewModelScope.launch(Dispatchers.IO) {
            _games.value = Resource.Loading()
            val games = repository.getGames()

            if (games != null) {
                _games.value = Resource.Success(games)
            }
            else {
                _games.value = Resource.Error(Throwable("No Games"))
            }
        }
    }
}