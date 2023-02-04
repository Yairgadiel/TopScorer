package com.example.topscorer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.topscorer.di.Player
import com.example.topscorer.ui.theme.TopScorerTheme
import com.google.android.exoplayer2.ExoPlayer
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), LifecycleEventObserver {

    @Player @Inject lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycle.addObserver(this)

        super.onCreate(savedInstanceState)
        setContent {
            TopScorerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_PAUSE -> {
                player.pause()
            }
            Lifecycle.Event.ON_RESUME -> {
                player.playWhenReady = true
            }
            Lifecycle.Event.ON_DESTROY -> {
                player.release()
            }
            else -> {}
        }
    }
}