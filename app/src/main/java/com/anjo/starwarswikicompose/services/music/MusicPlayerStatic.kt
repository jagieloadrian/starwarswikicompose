package com.anjo.starwarswikicompose.services.music

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import com.anjo.starwarswikicompose.R

object MusicPlayerStatic {
    private var player = MediaPlayer()
    private var shouldPlayMusic = true

    fun createMusic(context: Context) {
        player.setAudioAttributes(AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build())
        player = MediaPlayer.create(context, R.raw.cantinaband)
        player.isLooping = true
    }

    fun playMusic() {
        player.start()
    }

    fun pauseMusic() {
        player.pause()
    }

    fun shouldPlayMusic(): Boolean {
        return shouldPlayMusic
    }

    fun changeShouldPlayMusic() {
        shouldPlayMusic = !shouldPlayMusic
    }
}