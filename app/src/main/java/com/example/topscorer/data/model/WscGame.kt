package com.example.topscorer.data.model

data class WscGame(
    val awayTeamName: String,
    val gameId: String,
    val gameTime: String,
    val homeTeamName: String,
    val name: String,
    val primeStory: PrimeStory?
)