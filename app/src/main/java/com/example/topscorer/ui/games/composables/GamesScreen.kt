package com.example.topscorer.ui.games.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.topscorer.data.model.GameList
import com.example.topscorer.data.model.PrimeStory
import com.example.topscorer.ui.games.GamesViewModel
import com.example.topscorer.ui.destinations.StoriesScreenDestination
import com.example.topscorer.util.Resource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination()
@Composable
fun GamesScreen(viewModel: GamesViewModel = hiltViewModel(), navigator: DestinationsNavigator) {
    val state: Resource<GameList> by viewModel.games.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is Resource.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is Resource.Error -> Text(
                text = (state as Resource.Error<GameList>).data?.message ?: "Unknown error",
                modifier = Modifier.align(Alignment.Center)
            )

            is Resource.Success -> GameList((state as Resource.Success<GameList>).data, navigator)
        }
    }
}

@Composable
fun GameList(gameList: GameList, navigator: DestinationsNavigator) {
    // Get all games having a 'wscGame' property and a 'primeStory'
    val games = gameList.response.filter { it.wscGame?.primeStory != null }

    val onGameClick : (PrimeStory) -> Unit = { navigator.navigate(StoriesScreenDestination(it)) }

    LazyColumn() {
        items(games.size) {
            GameItem(game = games[it], onGameClick = onGameClick)
        }
    }
}