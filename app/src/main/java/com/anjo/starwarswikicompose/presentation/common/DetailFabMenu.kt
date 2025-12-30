package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.material3.animateFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.traversalIndex
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.utils.Constants.IMAGE_NOT_FOUND
import com.anjo.starwarswikicompose.utils.TestTags.FAB_MENU_TAG
import com.anjo.starwarswikicompose.utils.addImageFunction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DetailObjectFabMenu(
        navController: NavHostController,
        couldRemoveObject: Boolean = false,
        saveImageInDatabase: (String) -> Unit,
        removeObjectFromDatabase: () -> Unit,
        updateObjectFab: () -> Unit) {
    var fabExtended by remember { mutableStateOf(false) }
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary

    FloatingActionButtonMenu(expanded = fabExtended,
            button = {
                ToggleFloatingActionButton(
                        modifier =
                            Modifier
                                    .clip(RoundedCornerShape(MEDIUM_PADDING))
                                    .semantics {
                                        traversalIndex = -1f
                                        stateDescription = if (fabExtended) "Expanded" else "Collapsed"
                                        contentDescription = "Toggle menu"
                                    }
                                    .animateFloatingActionButton(
                                            visible = true,
                                            alignment = Alignment.BottomEnd,
                                    )
                                .testTag(FAB_MENU_TAG),
                        checked = fabExtended,
                        onCheckedChange = { fabExtended = !fabExtended },
                        containerColor = { progress -> lerp(primaryColor, secondaryColor, progress) },

                        ) {
                    val imageVector by remember {
                        derivedStateOf {
                            if (checkedProgress > 0.5f) Icons.Filled.Close else Icons.Filled.Add
                        }
                    }
                    Icon(
                            painter = rememberVectorPainter(imageVector),
                            contentDescription = null,
                            modifier = Modifier.animateIcon({ checkedProgress }),
                            tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }) {

        val items = generateItems(navController = navController,
                saveImageInDatabase = saveImageInDatabase,
                removeObjectFromDatabase = removeObjectFromDatabase,
                updateObjectFab = updateObjectFab,
                couldRemoveObject = couldRemoveObject)

        items.forEachIndexed { i, item ->
            FloatingActionButtonMenuItem(
                    modifier = Modifier.semantics {
                        isTraversalGroup = true
                        if (i == items.size - 1) {
                            customActions =
                                listOf(
                                        CustomAccessibilityAction(
                                                label = "Close menu",
                                                action = {
                                                    fabExtended = false
                                                    true
                                                },
                                        )
                                )
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    onClick = {
                        item.second.third.invoke()
                        fabExtended = false
                    },
                    icon = {
                        if (item.second.first is ImageVector) {
                            Icon(imageVector = item.second.first as ImageVector, contentDescription = null)
                        } else {
                            Icon(painter = item.second.first as Painter, contentDescription = null)
                        }
                    },
                    text = { Text(text = item.second.second) },
            )
        }
    }
}

@Composable
private fun generateItems(
        refreshScope: CoroutineScope = rememberCoroutineScope(),
        snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
        clipManager: Clipboard = LocalClipboard.current,
        navController: NavHostController,
        couldRemoveObject: Boolean,
        saveImageInDatabase: (String) -> Unit,
        removeObjectFromDatabase: () -> Unit,
        updateObjectFab: () -> Unit
): List<Pair<String, Triple<Any, String, () -> Any>>> {
    val items = mutableListOf(
            "AddImage" to Triple(Icons.Filled.Add, "Add Image") {
                refreshScope.launch {
                    addImageFunction(clipManager, navController,
                            runSaving = { string ->
                                saveImageInDatabase(string)
                            },
                            navControllerSnackBar = {
                                snackBarHostState.showSnackbar(IMAGE_NOT_FOUND)
                            })
                }
            },
            "CopyObject" to Triple(painterResource(R.drawable.baseline_content_copy_24), "Copy Object"
            ) { updateObjectFab() }
    )
    if (couldRemoveObject) {
        items.add(
                "DeleteObject" to Triple(Icons.Filled.Delete, "Delete Object") {
                    removeObjectFromDatabase()
                    refreshScope.launch {
                        snackBarHostState.showSnackbar("DELETE a given Object test")
                    }
                }
        )
    }

    return items.toList()
}