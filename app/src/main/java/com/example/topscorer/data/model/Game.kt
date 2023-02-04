package com.example.topscorer.data.model

data class Game(
    val WSCGameId: String,
    val fixture: Fixture,
    val league: League,
    val storifyMeHandle: String,
    val storifyMeID: Int,
    val teams: Teams,
    val wscGame: WscGame?
)