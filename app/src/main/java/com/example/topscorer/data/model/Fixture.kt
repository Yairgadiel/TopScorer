package com.example.topscorer.data.model

data class Fixture(
    val date: String,
    val id: Int,
    val referee: String,
    val status: Status,
    val timestamp: Long,
    val timezone: String,
    val venue: Venue
)