package com.anjo.starwarswikicompose.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import com.anjo.starwarswikicompose.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.anjo.starwarswikicompose.ui.theme.PAGING_INDICATOR_SPACING
import com.anjo.starwarswikicompose.ui.theme.PAGING_INDICATOR_WIDTH

@Composable
fun IndicatorDot(
        isSelected: Boolean,
        selectedColor: Color = Color.Yellow,
        unSelectedColor: Color = Color.LightGray) {
    val color = if (isSelected) selectedColor else unSelectedColor
    Box(
            modifier = Modifier
                    .padding(PAGING_INDICATOR_SPACING)
                    .size(PAGING_INDICATOR_WIDTH)
                    .clip(CircleShape)
                    .background(color)
    )
}

@Composable
fun DotsIndicator(
        modifier: Modifier = Modifier,
        totalDots: Int,
        selectedIndex: Int,
        selectedColor: Color = Color.Yellow,
        unSelectedColor: Color = Color.LightGray) {
    LazyRow(
            modifier = modifier.height(NAME_PLACEHOLDER_HEIGHT)
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
    ) {
        items(totalDots) { index ->
            val isSelected = index == selectedIndex
            IndicatorDot(isSelected,
                    selectedColor,
                    unSelectedColor)
            if (selectedIndex != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}