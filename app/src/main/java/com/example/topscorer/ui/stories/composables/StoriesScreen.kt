package com.example.topscorer.ui.stories.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.topscorer.data.model.Page
import com.example.topscorer.data.model.PrimeStory
import com.example.topscorer.ui.stories.StoriesViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun StoriesScreen(viewModel: StoriesViewModel = hiltViewModel(), story: PrimeStory, navigator: DestinationsNavigator) {

    viewModel.initPlayer(story)

    var currIndex by rememberSaveable { mutableStateOf(0) }

    val exoPlayer = viewModel.player

    exoPlayer.addListener(object : Player.Listener {
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)

            if (reason == Player.MEDIA_ITEM_TRANSITION_REASON_AUTO) {
                currIndex = exoPlayer.currentMediaItemIndex
            }
        }
    })

    Stories(exoPlayer = exoPlayer, pages = story.pages, currIndex = currIndex) {
        when (it) {
            StoryNavigation.PREVIOUS -> {
                if (currIndex > 0) {
                    exoPlayer.seekToPreviousMediaItem()
                    currIndex--
                }
            }
            StoryNavigation.NEXT -> {
                if (currIndex < exoPlayer.mediaItemCount -1) {
                    exoPlayer.seekToNextMediaItem()
                    currIndex++
                }
            }
            StoryNavigation.BACK -> navigator.navigateUp()
        }
    }
}

@Destination
@Composable
fun Stories(exoPlayer: ExoPlayer, pages: List<Page>, currIndex: Int, onNavigationGesture : (StoryNavigation) -> Unit) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .padding(vertical = 4.dp)
        .clip(RoundedCornerShape(4.dp))
    ) {

        Icon(
            Icons.Default.Close,
            tint = Color.White,
            contentDescription = "close",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(32.dp)
                .zIndex(2f)
                .clickable { onNavigationGesture(StoryNavigation.BACK) }
        )

        Row(modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth()
            .wrapContentHeight()
            .zIndex(3f)
        ) {
            for (i in pages.indices) {
                Spacer(modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .padding(2.dp)
                    .background(if (i < currIndex) Color.LightGray else Color.DarkGray))
            }
        }

        StoryPlayer(exoPlayer = exoPlayer, onNavigationGesture = onNavigationGesture)
    }
}

@Composable
fun StoryPlayer(exoPlayer: ExoPlayer, onNavigationGesture: (StoryNavigation) -> Unit) {
    val context = LocalContext.current

    Box(modifier = Modifier.wrapContentHeight()) {
        ClickArea(modifier = Modifier.align(Alignment.CenterStart)) {
            onNavigationGesture(StoryNavigation.PREVIOUS)
        }

        DisposableEffect(
            AndroidView(
                factory = {
                    StyledPlayerView(context).apply {
                        hideController()
                        useController = false
                        player = exoPlayer
                    }
                },
                modifier = Modifier
                    .wrapContentHeight()
            )
        ) {
            onDispose {
                exoPlayer.clearMediaItems()
                exoPlayer.stop()
            }
        }

        ClickArea(modifier = Modifier.align(Alignment.CenterEnd)) {
            onNavigationGesture(StoryNavigation.NEXT)
        }
    }
}

@Composable
fun ClickArea(modifier: Modifier = Modifier, onClick : () -> Unit) {
    Column(modifier = modifier
        .fillMaxHeight()
        .width(90.dp)
        .clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) { onClick() }) {}
}

enum class StoryNavigation {
    PREVIOUS,
    NEXT,
    BACK
}