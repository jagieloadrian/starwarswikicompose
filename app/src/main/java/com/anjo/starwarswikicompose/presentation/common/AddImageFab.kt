package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.navigation.NavHostController
import com.anjo.starwarswikicompose.ui.theme.MEDIUM_PADDING
import com.anjo.starwarswikicompose.ui.theme.PAGING_INDICATOR_WIDTH
import com.anjo.starwarswikicompose.ui.theme.mainBackgroundColors
import com.anjo.starwarswikicompose.ui.theme.mainContentColor
import com.anjo.starwarswikicompose.utils.Constants
import com.anjo.starwarswikicompose.utils.Constants.FAB_BUTTON_TAG
import com.anjo.starwarswikicompose.utils.addImageFunction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddImageFabWrap(
        fabExtended: Boolean,
        clipManager: ClipboardManager,
        navController: NavHostController,
        selectedName: String,
        runSaving: (String) -> Unit,
        refreshScope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
        enable: Boolean,
) {
    if (enable) {
        AddImageFab(extended = fabExtended) {
            addImageFunction(clipManager, navController,
                    runSaving = { string ->
                        runSaving(string)
                    },
                    runSavingSnackBar = {
                        refreshScope.launch {
                            snackBarHostState.showSnackbar("${Constants.SAVE_IN_PREFIX}${selectedName}")
                        }
                    },
                    navControllerSnackBar = {
                        refreshScope.launch {
                            snackBarHostState.showSnackbar(Constants.IMAGE_NOT_FOUND)
                        }
                    })
        }
    }
}

@Composable
fun AddImageFab(
        modifier: Modifier = Modifier,
        extended: Boolean,
        onClick: () -> Unit,
) {
    FloatingActionButton(
            modifier = modifier,
            onClick = onClick,
            backgroundColor = MaterialTheme.colors.mainBackgroundColors,
            shape = RoundedCornerShape(MEDIUM_PADDING)
    ) {
        Row(modifier = Modifier.padding(MEDIUM_PADDING),
                verticalAlignment = Alignment.CenterVertically) {
            Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = FAB_BUTTON_TAG,
                    tint = MaterialTheme.colors.mainContentColor
            )
            AnimatedVisibility(visible = extended) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(Modifier.padding(PAGING_INDICATOR_WIDTH))
                    Text(text = "Add Image from Clipboard",
                            color = MaterialTheme.colors.mainContentColor)
                }
            }
        }
    }
}
