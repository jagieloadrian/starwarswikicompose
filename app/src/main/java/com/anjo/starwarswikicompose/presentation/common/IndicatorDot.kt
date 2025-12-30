package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.anjo.starwarswikicompose.ui.theme.PAGING_INDICATOR_SPACING
import com.anjo.starwarswikicompose.ui.theme.PAGING_INDICATOR_WIDTH
import com.anjo.starwarswikicompose.ui.theme.TOP_BAR_HEIGHT
import com.anjo.starwarswikicompose.utils.TestTags.INDICATOR_DOTS_TAG

@Composable
fun DotsIndicator(
        modifier: Modifier = Modifier,
        totalDots: Int,
        selectedIndex: Int,
        selectedColor: Color = Color.Yellow,
        unSelectedColor: Color = Color.LightGray,
) {
    LazyRow(
            modifier = modifier.height(TOP_BAR_HEIGHT)
                .testTag(INDICATOR_DOTS_TAG),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
    ) {
        items(totalDots) { index ->
            val isSelected = index == selectedIndex
            IndicatorDot(isSelected,
                    selectedColor,
                    unSelectedColor)
        }
    }
}

@Composable
private fun IndicatorDot(
        isSelected: Boolean,
        selectedColor: Color = Color.Yellow,
        unSelectedColor: Color = Color.LightGray,
) {
    val color = if (isSelected) selectedColor else unSelectedColor
    Box(
            modifier = Modifier
                    .padding(PAGING_INDICATOR_SPACING)
                    .size(PAGING_INDICATOR_WIDTH)
                    .clip(CircleShape)
                    .background(color)
    )
}
