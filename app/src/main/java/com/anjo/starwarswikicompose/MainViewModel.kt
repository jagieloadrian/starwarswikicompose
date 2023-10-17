package com.anjo.starwarswikicompose

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.anjo.starwarswikicompose.R.raw.cantinaband
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    private val _musicPlayer = mutableStateOf(MediaPlayer())

    private val _pausedMusic = mutableStateOf(false)

    private val _mutedMusic = MutableStateFlow(true)
    val mutedMusic  = _mutedMusic

    init {
        playMusic()
        volumeUp()
    }

    fun createMusic(context: Context) {
        _musicPlayer.value = MediaPlayer.create(context, cantinaband)
        _musicPlayer.value.isLooping = true
    }

    fun pauseMusic() {
        _pausedMusic.value = true
        _musicPlayer.value.pause()
    }

    fun playMusic() {
        _pausedMusic.value = false
        _musicPlayer.value.start()
    }

    fun muteMusic() {
        _mutedMusic.value = true
        _musicPlayer.value.setVolume(0.01F, 0.01F)
    }

    fun volumeUp() {
        _mutedMusic.value = false
        _musicPlayer.value.setVolume(1.0F, 1.0F)
    }

}