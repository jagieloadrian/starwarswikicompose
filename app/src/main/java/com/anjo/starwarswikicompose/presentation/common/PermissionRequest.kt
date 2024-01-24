package com.anjo.starwarswikicompose.presentation.common

import android.Manifest
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.SMALL_BORDER
import com.anjo.starwarswikicompose.ui.theme.SMALL_PADDING
import com.anjo.starwarswikicompose.ui.theme.mainBackgroundColors
import com.anjo.starwarswikicompose.ui.theme.reverseMainBackgroundColors
import com.anjo.starwarswikicompose.utils.getLocalHeight
import com.anjo.starwarswikicompose.utils.getLocalWidth
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionScreen(periodicWorker: () -> Unit,
                     onDismissAction: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val postNotificationPerm = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
        if (postNotificationPerm.status.isGranted) {
            periodicWorker()
            return
        } else {
            AccessPermissionBox(postNotificationPerm) { onDismissAction() }
        }
    } else {
        periodicWorker()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AccessPermissionBox(
        postNotificationPerm: PermissionState,
        onDismissAction : () -> Unit
) {
    val height = ((getLocalHeight()/3)).dp
    val halfWidth = (getLocalWidth() / 3) * 2
    Dialog(onDismissRequest = onDismissAction) {
        Card(
                modifier = Modifier
                        .height(height)
                        .fillMaxWidth()
                        .padding(16.dp),
                shape = RoundedCornerShape(16.dp)) {
            Box(modifier = Modifier.fillMaxSize()
                    .paint(painter = painterResource(R.drawable.stars_image),
                            contentScale = ContentScale.FillBounds),
                    contentAlignment = Alignment.Center) {
                Column(modifier = Modifier
                        .clip(RoundedCornerShape(MEDIUM_PADDING))
                        .background(brush = Brush.linearGradient(listOf(
                                Color.Yellow, Color.Red, Color.Blue
                        )))
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
                                    .padding(SMALL_PADDING),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        postNotificationPerm.launchPermissionRequest()
                       onDismissAction()
                    }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.mainBackgroundColors
                    )
                    ) {
                        Text("Request permission",
                                color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { onDismissAction() }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.mainBackgroundColors)) {
                        Text("Cancel",
                                color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
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