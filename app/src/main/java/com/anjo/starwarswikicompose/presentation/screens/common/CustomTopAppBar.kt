package com.anjo.starwarswikicompose.presentation.screens.common

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.media.AudioManager.STREAM_MUSIC
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.domain.model.MenuItemData
import com.anjo.starwarswikicompose.navigation.Screen
import com.anjo.starwarswikicompose.ui.theme.HOME_ICON_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.SOLOFontName
import com.anjo.starwarswikicompose.ui.theme.TOP_BAR_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.topAppBarContentColor
import com.anjo.starwarswikicompose.ui.theme.topAppBarHomeBackgroundColor

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomTopAppBar(navHostController: NavHostController) {
    val muted = remember { mutableStateOf(false) }
    val listItems = getMenuItemsList(muted)
    val openDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    var expanded by remember {
        mutableStateOf(false)
    }
    if (openDialog.value) {
        InfoDialog { openDialog.value = false }
    }

    TopAppBar(modifier = Modifier.fillMaxWidth()
            .height(TOP_BAR_HEIGHT),
            backgroundColor = MaterialTheme.colors.topAppBarHomeBackgroundColor,
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
                            color = MaterialTheme.colors.topAppBarContentColor,
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
                            tint = MaterialTheme.colors.topAppBarContentColor)
                }
            },
            actions = {
                IconButton(onClick = {
                    expanded = true
                }) {
                    Icon(imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.options),
                            modifier = Modifier.height(HOME_ICON_HEIGHT),
                            tint = MaterialTheme.colors.topAppBarContentColor)
                }
                DropdownMenu(
                        modifier = Modifier.width(width = 150.dp)
                                .background(MaterialTheme.colors.topAppBarHomeBackgroundColor),
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        },
                        offset = DpOffset(x = (-102).dp, y = (-64).dp),
                        properties = PopupProperties()
                ) {


                    listItems.forEach { menuItemData ->
                        DropdownMenuItem(
                                onClick = {
                                    RunProperlyAction(menuItemData, context, muted, openDialog, audioManager)
                                    expanded = false
                                },
                                enabled = true
                        ) {

                            Icon(
                                    painter = menuItemData.icon,
                                    contentDescription = menuItemData.text,
                                    tint = MaterialTheme.colors.topAppBarContentColor,
                            )

                            Spacer(modifier = Modifier.width(width = 8.dp))

                            Text(
                                    text = menuItemData.text,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colors.topAppBarContentColor
                            )
                        }
                    }
                }

            }
    )
}


fun getMenuItemsList(muted: MutableState<Boolean>): ArrayList<MenuItemData> {
    val listItems = ArrayList<MenuItemData>()

    listItems.add(MenuItemData.Notes)
    listItems.add(MenuItemData.Mail)
    listItems.add(MenuItemData.Info)
    if (muted.value) {
        listItems.add(MenuItemData.Sound)
    } else {
        listItems.add(MenuItemData.Mute)
    }

    return listItems
}

fun RunProperlyAction(menuItemData: MenuItemData, context: Context, muted: MutableState<Boolean>,
                      openDialog: MutableState<Boolean>,
                      audioManager: AudioManager) {

    val maxVol: Int = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM)
    when (menuItemData) {
        MenuItemData.Notes -> Toast.makeText(context, "You choose: ${MenuItemData.Notes.text}", Toast.LENGTH_SHORT)
                .show()

        MenuItemData.Mail  -> Toast.makeText(context, "You choose: ${MenuItemData.Mail.text}", Toast.LENGTH_SHORT)
                .show()

        MenuItemData.Info  -> {
            openDialog.value = true
        }

        MenuItemData.Sound -> {
            muted.value = false
            audioManager.setStreamVolume(STREAM_MUSIC, maxVol, 0)
        }

        MenuItemData.Mute  -> {
            muted.value = true
            audioManager.setStreamVolume(STREAM_MUSIC, 0, 0)
        }
    }
}
