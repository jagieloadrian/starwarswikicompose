package com.anjo.playstorepublish

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.FileContent
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.androidpublisher.AndroidPublisher
import com.google.api.services.androidpublisher.AndroidPublisherScopes
import com.google.api.services.androidpublisher.model.AppEdit
import com.google.api.services.androidpublisher.model.Bundle
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.ServiceAccountCredentials
import java.io.File
import java.io.FileInputStream
import kotlin.system.exitProcess

private const val SCRIPT_FAILED_CODE = -1
private const val AAB_CONTENT_MIME_TYPE = "application/octet-stream"

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        try {
            println("Started application upload script")
            val appId = args[0]
            val appPath = args[1]
            val credentialsPath = args[2]
            println("Received arguments\nappId: $appId\nappPath: $appPath\ncredentialsPath: $credentialsPath")
            uploadApp(appId, appPath, credentialsPath)
        } catch (e: Exception) {
            println(" ${e::class.simpleName} ${e.message}")
            exitProcess(SCRIPT_FAILED_CODE)
        }
    }

    private fun uploadApp(appId: String, appPath: String, credentialsPath: String) {
        val publisher = getPublisherData(credentialsPath)

        val edit: AppEdit = publisher.edits().insert(appId, null).execute()

        println("Created edit: ${edit.id}")

        val app: Bundle = publisher.edits().bundles().upload(
                appId,
                edit.id,
                FileContent(AAB_CONTENT_MIME_TYPE, File(appPath)))
                .execute()

        println("Uploaded apk versionCode: ${app.versionCode}")

        publisher.edits().commit(appId, edit.id).execute()
        println("Committed edit with a new apk")
    }

    private fun getPublisherData(
            credentialsPath: String): AndroidPublisher {
        val credentials = ServiceAccountCredentials
                .fromStream(FileInputStream(credentialsPath))
                .createScoped(AndroidPublisherScopes.all())
        return AndroidPublisher.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory(),
                setHttpTimeout(HttpCredentialsAdapter(credentials))
        ).setApplicationName("Google Play APP upload")
                .build()
    }

    private fun setHttpTimeout(requestInitializer: HttpRequestInitializer) = HttpRequestInitializer { request ->
        requestInitializer.initialize(request)
        request.connectTimeout = 3 * 60000
        request.readTimeout = 3 * 60000
    }
}