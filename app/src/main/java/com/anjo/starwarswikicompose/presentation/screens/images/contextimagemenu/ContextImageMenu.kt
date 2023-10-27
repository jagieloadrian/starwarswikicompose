package com.anjo.starwarswikicompose.presentation.screens.images.contextimagemenu

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.anjo.starwarswikicompose.domain.model.imageslider.ActionType
import com.anjo.starwarswikicompose.domain.model.imageslider.ClipBoardAction
import com.anjo.starwarswikicompose.utils.getLocalWidth

@SuppressLint("SuspiciousIndentation")
@Composable
fun ImageItemMenu(
        modifier: Modifier = Modifier,
        isContextMenuVisible: Boolean,
        pressOffset: DpOffset,
        onDismissRequest: () -> Unit,
        itemHeight: Dp,
        onClick:(ClipBoardAction)->Unit,
        dropdownItems: List<ClipBoardAction>
) {
    val localWidth = (getLocalWidth() / 2).dp

    DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = onDismissRequest,
            offset = pressOffset.copy(
                    y = pressOffset.y - itemHeight
            )
    ) {
        dropdownItems.forEach { objectItem ->
            DropdownMenuItem(onClick = { onClick(objectItem) }) {
                Text(text = objectItem.actionName,
                        maxLines = 2,
                        modifier = modifier
                                .width(localWidth))
            }
        }
    }
}

fun runContextAction(
        objectItem: ClipBoardAction, context: Context,
        contextImageModelView: ContextImageModelView) {
    return when (objectItem.action) {
        ActionType.SAVE_TO_OBJECT    -> saveInRoom(objectItem, contextImageModelView, context)
        ActionType.SAVE_TO_CLIPBOARD -> copyToClipboard(context, objectItem.photoUrl)
    }
}