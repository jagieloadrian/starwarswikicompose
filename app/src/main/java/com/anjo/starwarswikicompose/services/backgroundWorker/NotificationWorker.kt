package com.anjo.starwarswikicompose.services.backgroundWorker

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.anjo.starwarswikicompose.utils.Constants.NOTIFICATION_CHANNEL
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
        @Assisted context: Context,
        @Assisted workerParameters: WorkerParameters,
        private val notifyBuilder: NotificationCompat.Builder,
        private val notifyManager: NotificationManagerCompat,
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val id = inputData.getLong(NOTIFICATION_CHANNEL, 0).toInt()
        sendNotification(id)

        return Result.success()
    }

    @SuppressLint("MissingPermission") // checked in compose
    private fun sendNotification(id: Int) {
        notifyManager.notify(id, notifyBuilder.build())
    }
}