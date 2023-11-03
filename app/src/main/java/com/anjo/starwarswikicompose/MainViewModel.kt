package com.anjo.starwarswikicompose

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.anjo.starwarswikicompose.R.raw.cantinaband
import com.anjo.starwarswikicompose.services.backgroundWorker.NotificationWorker
import com.anjo.starwarswikicompose.utils.Constants.NOTIFICATION_WORK_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
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

    fun addPeriodicWorker(context: Context) {
        val checkIfExist = WorkManager.getInstance(context).getWorkInfosByTag(NOTIFICATION_WORK_TAG).get()
                .filterNot { workInfo -> workInfo.state.isFinished }
                .count()
        if(checkIfExist == 0) {
            val workRequest =
                PeriodicWorkRequestBuilder<NotificationWorker>(7, TimeUnit.DAYS).addTag(NOTIFICATION_WORK_TAG).build()
            WorkManager.getInstance(context).enqueue(workRequest)
            Log.e("WORKERS", workRequest.toString())
        }
    }

    fun cancelAllWorkers(context: Context) {
        WorkManager.getInstance(context).cancelAllWorkByTag(NOTIFICATION_WORK_TAG)
    }
}