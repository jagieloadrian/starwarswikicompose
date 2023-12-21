package com.anjo.starwarswikicompose.services.di

import android.app.NotificationChannel
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.anjo.starwarswikicompose.MainActivity
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.utils.Constants.ASKING_FOR_USER
import com.anjo.starwarswikicompose.utils.Constants.DESCRIPTION_ASKING_FOR_USER
import com.anjo.starwarswikicompose.utils.Constants.GO_TO_APP
import com.anjo.starwarswikicompose.utils.Constants.NOTIFICATION_CHANNEL
import com.anjo.starwarswikicompose.utils.Constants.NOTIFICATION_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Singleton
    @Provides
    fun provideNotificationBuilder(
            @ApplicationContext context: Context,
    ): NotificationCompat.Builder {

        val targetIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(targetIntent)
            getPendingIntent(0, FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT)
        }

        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.mipmap.sw_compose_icon_round)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentTitle(ASKING_FOR_USER)
                .setContentText(DESCRIPTION_ASKING_FOR_USER)
                .setShowWhen(false)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(0, GO_TO_APP, pendingIntent)
    }

    @Singleton
    @Provides
    fun provideNotificationManager(
            @ApplicationContext context: Context,
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        val channel = NotificationChannel(
                NOTIFICATION_CHANNEL,
                NOTIFICATION_NAME,
                IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
        return notificationManager
    }
}