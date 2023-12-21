package com.anjo.starwarswikicompose.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

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


val Colors.welcomeScreenBackgroundColor
    get() = if (isLight) Color.White else Color.Black

val Colors.welcomeScreenImageBackgroundColor
    get() = if (isLight) listOf(Color.Black, ShimmerMediumGray, Color.White) else listOf(Color.White, ShimmerMediumGray,
            Color.Black)


val Colors.titleColor
    get() = if (isLight) DarkGray else LightGray

val Colors.descriptionColor
    get() = if (isLight) DarkGray.copy(alpha = 0.5f) else LightGray.copy(0.5f)

val Colors.welcomeImageBackground
    get() = if (isLight) Color.White else Color.Black

val Colors.mainContentColor: Color
    get() = if (isLight) Color.White else LightGray

val Colors.mainBackgroundColors: Color
    get() = if (isLight) GoldColor else GoldOrangeColor


val Colors.reverseMainBackgroundColors: Color
    get() = if (isLight) GoldOrangeColor else GoldColor