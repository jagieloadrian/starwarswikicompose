package com.anjo.starwarswikicompose.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

val Purple700 = Color(0xFF3700B3)

val LightGray = Color(0xFFD8D8D8)
val DarkGray = Color(0xFF2A2A2A)

val GoldColor = Color(0xFFD5B322)
val GoldOrangeColor = Color(0xFFFF5722)

val ShimmerMediumGray = Color(0xFFE3E3E3)
val ShimmerDarkGray = Color(0xFF1D1D1D)

val BOXES_COLORS = listOf(Color.Yellow, Color.Red, Purple700)
val CLICKABLE_BOXES_COLORS = listOf(Color.Yellow, Color.Red)
val RELATED_BOXES_COLORS = listOf(Color.Yellow, Color.Red, Color.Blue)
val SHIMMER_COLORS = RELATED_BOXES_COLORS

fun ColorScheme.isLight() = this.background.luminance() > 0.5

val ColorScheme.welcomeScreenImageBackgroundColor
    get() = if (isLight()) listOf(Color.Black, ShimmerMediumGray, Color.White)
    else listOf(Color.White, ShimmerMediumGray, Color.Black)