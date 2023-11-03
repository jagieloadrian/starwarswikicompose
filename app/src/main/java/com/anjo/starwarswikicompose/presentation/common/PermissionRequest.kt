package com.anjo.starwarswikicompose.presentation.common

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionScreen(periodicWorker: () -> Unit, composable: @Composable () -> Unit) {
    var setToComposable by remember { mutableStateOf(true) }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        setToComposable = false
        val postNotificationPerm = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
        if (postNotificationPerm.status.isGranted) {
            periodicWorker()
            setToComposable = true
        } else {
            Box(modifier = Modifier,
                    contentAlignment = Alignment.Center) {
                Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                    val textToShow = if (postNotificationPerm.status.shouldShowRationale) {
                        "The notification reminds you about the news in Star Wars world. Please grant the permission."
                    } else {
                        "Notification are not available"
                    }
                    Text(textToShow)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        postNotificationPerm.launchPermissionRequest()
                        setToComposable = true
                    }) {
                        Text("Request permission")
                    }
                }
            }
        }
    }
    if (setToComposable) {
        composable()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun checkNotificationPolicyAccess(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val postNotificationPerm = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
        return postNotificationPerm.status.isGranted
    }
    return true
}