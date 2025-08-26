package com.anjo.starwarswikicompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
        primary = GoldOrangeColor, //mainBackground
        secondary = GoldColor, //reverseBackground
        tertiary = Color.Black, //welcomeScreen and welcome imageBackground
        onPrimary = Color.White,
        onSecondary = Color.White, //mainContent
        onTertiary = Color.White, //title color,
        onError = Color.White
)

private val LightColorPalette = lightColorScheme(
        primary = GoldColor,
        secondary = GoldOrangeColor,
        tertiary = Color.White,
        onPrimary = Color.LightGray,
        onSecondary = Color.White,
        onTertiary = Color.Black,
        onError = Color.White
)

@Composable
fun StarWarsWikiComposeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}