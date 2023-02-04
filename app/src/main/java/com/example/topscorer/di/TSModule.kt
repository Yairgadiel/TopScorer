package com.example.topscorer.di

import android.app.Application
import android.content.res.AssetManager
import com.google.android.exoplayer2.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class TSModule {
    @Provides
    @Assets
    fun provideAssetManager(application: Application) : AssetManager {
        return application.assets
    }

    @Singleton
    @Provides
    @Player
    fun provideExoPlayer(application: Application) : ExoPlayer {
        return ExoPlayer.Builder(application.applicationContext).build()
    }
}