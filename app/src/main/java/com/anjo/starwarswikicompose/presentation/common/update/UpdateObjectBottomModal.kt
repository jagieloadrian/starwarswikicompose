package com.anjo.starwarswikicompose.presentation.common.update

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import com.anjo.starwarswikicompose.R
import com.anjo.starwarswikicompose.utils.TestTags.ADD_OBJECT_MAIN_SCREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CopyModelBottomModal(
        modifier: Modifier = Modifier,
        onDismiss: () -> Unit,
        updateObjectContent: @Composable () -> Unit
) {
    val sheetState = rememberBottomSheetState(initialValue = SheetValue.Hidden)

    ModalBottomSheet(
            modifier = modifier
                    .paint(painter = painterResource(R.drawable.night_sky_stars),
                            contentScale = ContentScale.FillBounds)
                    .testTag(ADD_OBJECT_MAIN_SCREEN),
            onDismissRequest = { onDismiss() },
            sheetState = sheetState,
            containerColor = Color.Transparent,
    ) {
        updateObjectContent()
    }
}