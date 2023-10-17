package com.anjo.starwarswikicompose

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.anjo.starwarswikicompose.R.raw.cantinaband
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    private val _musicPlayer = mutableStateOf(MediaPlayer())

    init {
        playMusic()
    }

    fun createMusic(context: Context) {
        _musicPlayer.value.setAudioAttributes(AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build())
        _musicPlayer.value = MediaPlayer.create(context, cantinaband)
        _musicPlayer.value.isLooping = true
    }

    fun pauseMusic() {
        _musicPlayer.value.pause()
    }

    fun playMusic() {
        _musicPlayer.value.start()
    }
}