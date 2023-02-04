package com.example.topscorer.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PrimeStory(
    val pages: List<Page>,
    val publishDate: String,
    val storyId: String,
    val storyThumbnail: String,
    val storyThumbnailSquare: String,
    val title: String
) : Parcelable