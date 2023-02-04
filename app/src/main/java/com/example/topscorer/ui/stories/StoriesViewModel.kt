package com.example.topscorer.ui.stories

import androidx.lifecycle.ViewModel
import com.example.topscorer.data.model.PrimeStory
import com.example.topscorer.di.Player
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoriesViewModel @Inject constructor(@Player val player: ExoPlayer) : ViewModel() {

    fun initPlayer(primeStory: PrimeStory) {
        player.prepare()
        player.playWhenReady = true

        if (player.mediaItemCount == 0) {
            player.setMediaItems(primeStory.pages.map { page ->
                MediaItem.Builder()
                    .setUri(page.videoUrl)
                    .build()
            })
        }
    }
}