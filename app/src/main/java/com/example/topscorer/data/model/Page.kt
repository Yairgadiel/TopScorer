package com.example.topscorer.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Page(
    val actionType: String?,
    val awayScore: Int,
    val duration: Int,
    val eventType: String?,
    val gameClock: String?,
    val homeScore: Int,
    val paggeId: String,
    val period: String?,
    val rating: Int,
    val title: String?,
    val videoUrl: String
) : Parcelable