package com.example.topscorer.data

import javax.inject.Inject

class GamesRepository @Inject constructor(private val localGamesDataSource: LocalScoresDataSource) {
    suspend fun getGames() = localGamesDataSource.getGames()
}