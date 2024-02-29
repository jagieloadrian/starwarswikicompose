package com.anjo.starwarswikicompose.presentation.common

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.MainViewModel
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.MenuItemData
import com.anjo.starwarswikicompose.domain.model.MenuItemData.Feedback
import com.anjo.starwarswikicompose.domain.model.MenuItemData.Info
import com.anjo.starwarswikicompose.domain.model.MenuItemData.Notes
import com.anjo.starwarswikicompose.domain.model.MenuItemData.Notification
import com.anjo.starwarswikicompose.domain.model.MenuItemData.Sound
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.presentation.screens.notes.CardNote
import com.anjo.starwarswikicompose.services.music.MusicPlayerStatic
import com.anjo.starwarswikicompose.ui.theme.HOME_ICON_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.ui.theme.TOP_BAR_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.mainBackgroundColors
import com.anjo.starwarswikicompose.ui.theme.mainContentColor
import com.anjo.starwarswikicompose.ui.theme.reverseMainBackgroundColors
import com.anjo.starwarswikicompose.utils.getLocalWidth

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomTopAppBar(
        navHostController: NavHostController,
        mainViewModel: MainViewModel = hiltViewModel(),
) {
    var soundOn by remember { mutableStateOf(MusicPlayerStatic.isPlayingMusic()) }
    val notification = remember { mutableStateOf(false) }
    val notificationPermissionRun = remember { mutableStateOf(false) }
    val listItems = listOf(Notes, Feedback, Info, Notification, Sound)
    val feedbackDialog = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val openNotes = remember { mutableStateOf(false) }
    val policy = checkNotificationPolicyAccess()
    val context = LocalContext.current
    val halfWidth = (getLocalWidth() / 2).dp
    var expanded by remember {
        mutableStateOf(false)
    }
    if (feedbackDialog.value) {
        FeedbackCard {
            feedbackDialog.value = false
        }
    }
    if (openDialog.value) {
        InfoDialog { openDialog.value = false }
    }
    if (openNotes.value) {
        CardNote(onDismissAction = { openNotes.value = false })
    }
    if (notificationPermissionRun.value) {
        PermissionLogic(notification, mainViewModel, context, policy) { notificationPermissionRun.value = false }
        notification.value = (policy && notification.value)
    }

    TopAppBar(modifier = Modifier.fillMaxWidth()
            .height(TOP_BAR_HEIGHT),
            backgroundColor = MaterialTheme.colors.mainBackgroundColors,
            title = {
                Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center) {
                    Text(
                            text = stringResource(R.string.app_name_top_bar),
                            fontFamily = SOLOFontName,
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .height(HOME_ICON_HEIGHT)
                                    .basicMarquee(iterations = Int.MAX_VALUE),
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.h4,
                            color = MaterialTheme.colors.mainContentColor,
                    )
                }
            },
            navigationIcon = {
                IconButton(
                        onClick = {
                            navHostController.navigate(Screen.Home.route)
                        }
                ) {
                    Icon(imageVector = Icons.Default.Home,
                            contentDescription = stringResource(R.string.home_icon),
                            modifier = Modifier.height(HOME_ICON_HEIGHT),
                            tint = MaterialTheme.colors.mainContentColor)
                }
            },
            actions = {
                IconButton(onClick = {
                    expanded = true
                }) {
                    Icon(imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.options),
                            modifier = Modifier.height(HOME_ICON_HEIGHT),
                            tint = MaterialTheme.colors.mainContentColor)
                }
                DropdownMenu(
                        modifier = Modifier.width(halfWidth)
                                .background(MaterialTheme.colors.mainBackgroundColors),
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        },
                        offset = DpOffset(x = halfWidth, y = (-64).dp),
                        properties = PopupProperties()
                ) {
                    listItems.forEach { menuItemData ->
                        DropdownMenuItem(
                                onClick = {
                                    expanded = if (menuItemData == Sound || menuItemData == Notification) {
                                        false
                                    } else {
                                        runProperlyAction(menuItemData,
                                                soundOn, openNotes, openDialog,
                                                feedbackDialog, notificationPermissionRun, mainViewModel)
                                        false
                                    }
                                },
                                enabled = true,
                        ) {
                            Icon(
                                    painter = menuItemData.icon,
                                    contentDescription = menuItemData.text,
                                    tint = MaterialTheme.colors.mainContentColor,
                                    modifier = Modifier.weight(2f)
                            )
                            Spacer(modifier = Modifier.weight(0.5f))
                            Text(
                                    text = menuItemData.text,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colors.mainContentColor,
                                    modifier = Modifier.weight(6f)
                            )
                            when (menuItemData) {
                                Sound        -> {
                                    Spacer(modifier = Modifier.weight(0.5f))
                                    Switch(checked = soundOn, onCheckedChange = {
                                        soundOn = it
                                        runProperlyAction(menuItemData,
                                                soundOn, openNotes, openDialog,
                                                feedbackDialog, notificationPermissionRun, mainViewModel)
                                    },
                                            modifier = Modifier.weight(2f),
                                            colors = SwitchDefaults.colors(
                                                    checkedThumbColor = Color.White,
                                                    uncheckedThumbColor = Color.White.copy(alpha = 0.6f),
                                                    checkedTrackColor = MaterialTheme.colors.reverseMainBackgroundColors,
                                                    uncheckedTrackColor = MaterialTheme.colors.reverseMainBackgroundColors,
                                                    checkedTrackAlpha = 0.8f
                                            ))
                                }

                                Notification -> {
                                    Spacer(modifier = Modifier.weight(0.5f))
                                    Switch(checked = notification.value, onCheckedChange = {
                                        notification.value = it
                                        runProperlyAction(menuItemData,
                                                soundOn, openNotes, openDialog,
                                                feedbackDialog, notificationPermissionRun,
                                                mainViewModel = mainViewModel)
                                    },
                                            modifier = Modifier.weight(2f),
                                            colors = SwitchDefaults.colors(
                                                    checkedThumbColor = Color.White,
                                                    uncheckedThumbColor = Color.White.copy(alpha = 0.6f),
                                                    checkedTrackColor = MaterialTheme.colors.reverseMainBackgroundColors,
                                                    uncheckedTrackColor = MaterialTheme.colors.reverseMainBackgroundColors,
                                                    checkedTrackAlpha = 0.8f
                                            ))
                                }

                                else         -> {
                                    Spacer(modifier = Modifier.weight(2.5f))
                                }
                            }
                        }
                    }
                }

            }
    )
}

@Composable
private fun PermissionLogic(
        notification: MutableState<Boolean>,
        mainViewModel: MainViewModel,
        context: Context,
        policy: Boolean,
        onDismissDialog: () -> Unit,
) {
    if (policy) {
        if (notification.value) {
            mainViewModel.addPeriodicWorker(context)
        } else {
            mainViewModel.cancelAllWorkers(context)
        }
    } else {
        PermissionScreen(periodicWorker = { notification.value = true }, { onDismissDialog() })
    }
}

fun runProperlyAction(
        menuItemData: MenuItemData,
        soundOn: Boolean,
        openNotes: MutableState<Boolean>,
        openDialog: MutableState<Boolean>,
        feedbackDialog: MutableState<Boolean>,
        notificationPermissionRun: MutableState<Boolean>,
        mainViewModel: MainViewModel,
) {
    when (menuItemData) {
        Notes        -> {
            openNotes.value = true
        }

        Feedback     -> {
            feedbackDialog.value = true
        }

        Info         -> {
            openDialog.value = true
        }

        Sound        -> {
            if (soundOn) {
                mainViewModel.playMusic()
            } else {
                mainViewModel.pauseMusic()
            }
        }

        Notification -> {
            notificationPermissionRun.value = true
        }
    }
}


