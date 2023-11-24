package com.anjo.starwarswikicompose.presentation.common

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_BORDER
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.mainBackgroundColors
import com.anjo.starwarswikicompose.ui.theme.reverseMainBackgroundColors
import com.anjo.starwarswikicompose.utils.getLocalWidth
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionScreen(periodicWorker: () -> Unit, composable: @Composable () -> Unit) {
    val halfWidth = getLocalWidth() / 2
    var setToComposable by remember { mutableStateOf(true) }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        setToComposable = false
        val postNotificationPerm = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
        if (postNotificationPerm.status.isGranted) {
            periodicWorker()
            setToComposable = true
        } else {
            Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                Column(modifier = Modifier
                        .border(SMALL_BORDER,
                                MaterialTheme.colors.reverseMainBackgroundColors,
                                RoundedCornerShape(MEDIUM_PADDING)),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                    val textToShow = if (postNotificationPerm.status.shouldShowRationale) {
                        "The notification reminds you about the news in Star Wars world.\nPlease grant the permission."
                    } else {
                        "Notification are not available.\nDo you want turn on notification?"
                    }
                    Text(textToShow,
                            modifier = Modifier.width(halfWidth.dp)
                                    .padding(SMALL_PADDING))
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        postNotificationPerm.launchPermissionRequest()
                        setToComposable = true
                    }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.mainBackgroundColors
                    )
                    ) {
                        Text("Request permission")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { setToComposable = true }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.mainBackgroundColors)) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
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