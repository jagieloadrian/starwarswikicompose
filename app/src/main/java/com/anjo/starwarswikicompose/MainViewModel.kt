package com.anjo.starwarswikicompose

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    fun playSound(context: Context) {
        val mp: MediaPlayer = MediaPlayer.create(context, R.raw.cantinaband)
                mp.isLooping = true
        mp.start()
    }
}