package com.anjo.starwarswikicompose.services.backgroundWorker

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.impl.utils.taskexecutor.TaskExecutor
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class NotificationWorkerTest {

    @RelaxedMockK
    lateinit var context: Context

    @RelaxedMockK
    lateinit var notification: Notification

    @RelaxedMockK
    lateinit var data: Data

    @RelaxedMockK
    lateinit var executor: TaskExecutor

    @RelaxedMockK
    lateinit var workerParameters: WorkerParameters

    @RelaxedMockK
    lateinit var notifyBuilder: NotificationCompat.Builder

    @RelaxedMockK
    lateinit var notifyManager: NotificationManagerCompat

    @InjectMockKs
    lateinit var notificationWorker: NotificationWorker

    @Test
    fun `given mocks when call doWork then return response`() = runBlocking {
        //given
        val mockContext = mockk<Context>() {

        }
        every { workerParameters.taskExecutor } returns executor
        every { workerParameters.inputData } returns data
        every { notifyBuilder.build() } returns notification
        every { notifyManager.notify(any(), any()) } answers {}

        //when
        notificationWorker = NotificationWorker(mockContext, workerParameters, notifyBuilder, notifyManager)
        val actual = notificationWorker.doWork()

        //then
        actual shouldNotBe null
    }
}