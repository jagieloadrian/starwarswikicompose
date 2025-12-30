package com.anjo.starwarswikicompose.presentation.common.appbars

import android.Manifest.permission.POST_NOTIFICATIONS
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.MainViewModel
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.MenuItemData.Feedback
import com.anjo.starwarswikicompose.domain.model.MenuItemData.Info
import com.anjo.starwarswikicompose.domain.model.MenuItemData.Notes
import com.anjo.starwarswikicompose.domain.model.MenuItemData.Notification
import com.anjo.starwarswikicompose.domain.model.MenuItemData.Sound
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.presentation.common.detail.getLocalWidth
import com.anjo.starwarswikicompose.presentation.common.menucomponents.FeedbackCard
import com.anjo.starwarswikicompose.presentation.common.menucomponents.InfoDialog
import com.anjo.starwarswikicompose.presentation.screens.notes.CardNote
import com.anjo.starwarswikicompose.services.music.MusicPlayerStatic
import com.anjo.starwarswikicompose.ui.theme.HOME_ICON_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SMALL_BORDER
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.ui.theme.TOP_BAR_HEIGHT
import com.anjo.starwarswikicompose.utils.TestTags.TOP_APP_BAR_TAG
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    var soundOn by remember { mutableStateOf(MusicPlayerStatic.shouldPlayMusic()) }
    val notification by mainViewModel.isNotificationsEnabled.collectAsState()
    val listItems = listOf(Notes, Feedback, Info, Notification, Sound)
    var feedbackDialog by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }
    var openNotes by remember { mutableStateOf(false) }
    val halfWidth = (getLocalWidth() / 2).dp
    var expanded by remember {
        mutableStateOf(false)
    }
    if (feedbackDialog) {
        FeedbackCard {
            feedbackDialog = false
        }
    }
    if (openDialog) {
        InfoDialog { openDialog = false }
    }
    if (openNotes) {
        CardNote(onDismissAction = { openNotes = false })
    }
    if (notification) {
        PermissionLogic(notification, mainViewModel)
    }

    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT)
            .testTag(TOP_APP_BAR_TAG),
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onSecondary,
            titleContentColor = MaterialTheme.colorScheme.onSecondary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary,
            subtitleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.app_name_top_bar),
                    fontFamily = SOLOFontName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(HOME_ICON_HEIGHT)
                        .basicMarquee(iterations = Int.MAX_VALUE),
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navHostController.navigate(Screen.Home.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.home_icon),
                    modifier = Modifier.height(TOP_BAR_HEIGHT),
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        actions = {
            IconButton(onClick = {
                expanded = true
            }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.options),
                    modifier = Modifier.height(TOP_BAR_HEIGHT),
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
            DropdownMenu(
                modifier = Modifier
                    .width(halfWidth)
                    .background(MaterialTheme.colorScheme.primary),
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(SMALL_BORDER, MaterialTheme.colorScheme.secondary),
                offset = DpOffset(x = halfWidth, y = (-64).dp),
                properties = PopupProperties()
            ) {
                listItems.forEach { menuItemData ->
                    DropdownMenuItem(
                        onClick = {
                            when (menuItemData) {
                                Notes -> {
                                    openNotes = true
                                }

                                Feedback -> {
                                    feedbackDialog = true
                                }

                                Info -> {
                                    openDialog = true
                                }

                                else -> {}
                            }
                            expanded = false
                        },
                        enabled = true,
                        text = {
                            Text(
                                text = menuItemData.text,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier.weight(6f)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = menuItemData.icon,
                                contentDescription = menuItemData.text,
                                tint = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier.weight(2f)
                            )
                        },
                        trailingIcon = {
                            when (menuItemData) {
                                Sound -> {
                                    MenuSwitchIcon(soundOn, Modifier.weight(2f)) {
                                        soundOn = it
                                        changeMusic(soundOn, mainViewModel)
                                    }
                                }

                                Notification -> {
                                    MenuSwitchIcon(notification, Modifier.weight(2f)) {
                                        mainViewModel.toggleNotifications(it)
                                    }
                                }

                                else -> {}
                            }
                        }
                    )
                }
            }
        })
}

@Composable
fun MenuSwitchIcon(
    checked: Boolean,
    modifier: Modifier,
    onCheckedChange: (Boolean) -> Unit
) {
    Switch(
        checked = checked, onCheckedChange = { onCheckedChange(it) },
        modifier = modifier,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            uncheckedThumbColor = Color.White.copy(alpha = 0.6f),
            checkedTrackColor = MaterialTheme.colorScheme.secondary,
            uncheckedTrackColor = MaterialTheme.colorScheme.secondary
        )
    )
}

@SuppressLint("InlinedApi")
@Composable
private fun PermissionLogic(
    notification: Boolean,
    mainViewModel: MainViewModel,
) {
    val context = LocalContext.current
    var shouldRunPermission by remember { mutableStateOf(false) }
    var permissionGranted by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(context, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        )
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionGranted = isGranted
        }
    )

    LaunchedEffect(shouldRunPermission) {
        requestPermissionLauncher.launch(POST_NOTIFICATIONS)
        delay(1000)
        shouldRunPermission = false
    }

    if (permissionGranted && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        shouldRunPermission = true
        mainViewModel.toggleNotifications(permissionGranted)
    } else {
        mainViewModel.toggleNotifications(notification)
    }
}

fun changeMusic(soundOn: Boolean, mainViewModel: MainViewModel) {
    if (soundOn) {
        mainViewModel.changeStateOfMusic()
        mainViewModel.playMusic()
    } else {
        mainViewModel.changeStateOfMusic()
        mainViewModel.pauseMusic()
    }
}