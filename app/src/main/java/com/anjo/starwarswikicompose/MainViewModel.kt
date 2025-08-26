package com.anjo.starwarswikicompose

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.anjo.starwarswikicompose.services.backgroundWorker.NotificationWorker
import com.anjo.starwarswikicompose.services.music.MusicPlayerStatic
import com.anjo.starwarswikicompose.services.usecases.stateusecase.StateUseCase
import com.anjo.starwarswikicompose.utils.Constants.NOTIFICATION_WORK_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@HiltViewModel
open class MainViewModel @Inject constructor(
        @param:ApplicationContext private val context: Context,
        private val useCases: StateUseCase
) : ViewModel() {
    private val _isNotificationsEnabled = MutableStateFlow(false)
    val isNotificationsEnabled: StateFlow<Boolean> = _isNotificationsEnabled

    init {
        readNotificationEnabled()
    }

    fun changeStateOfMusic() {
        MusicPlayerStatic.changeShouldPlayMusic()
    }

    fun createMusic() {
        MusicPlayerStatic.createMusic(context)
    }

    fun pauseMusic() {
        MusicPlayerStatic.pauseMusic()
    }

    fun playMusic() {
        if (MusicPlayerStatic.shouldPlayMusic()) {
            MusicPlayerStatic.playMusic()
        }
    }

    private fun readNotificationEnabled() {
        viewModelScope.launch {
            useCases.readNotificationEnabled().collect { newValue ->
                _isNotificationsEnabled.update { _ ->
                    newValue
                }
            }
        }
    }

    fun toggleNotifications(enabled: Boolean) {
        viewModelScope.launch {
            useCases.saveNotificationEnabled(enabled)
        }
        readNotificationEnabled()
        if (enabled) {
            addPeriodicWorker()
        } else {
            cancelAllWorkers()
        }
    }

    private fun addPeriodicWorker() {
        val checkIfExist = WorkManager.getInstance(context).getWorkInfosByTag(NOTIFICATION_WORK_TAG).get()
                .filterNot { workInfo -> workInfo.state.isFinished }
                .count()
        if (checkIfExist == 0) {
            val existingWorkPolicy = ExistingPeriodicWorkPolicy.KEEP
            val workRequest =
                PeriodicWorkRequestBuilder<NotificationWorker>(7, TimeUnit.DAYS)
                        .addTag(NOTIFICATION_WORK_TAG)
                        .build()
            WorkManager.getInstance(context)
                    .enqueueUniquePeriodicWork(NOTIFICATION_WORK_TAG, existingWorkPolicy, workRequest)
        }
    }

    private fun cancelAllWorkers() {
        WorkManager.getInstance(context).cancelAllWorkByTag(NOTIFICATION_WORK_TAG)
    }
}