package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

private val DarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = RoyalBlueDeep,
    primaryContainer = RoyalBlueCard,
    onPrimaryContainer = GoldLight,
    secondary = GoldWarm,
    onSecondary = RoyalBlueDeep,
    secondaryContainer = RoyalBlueElevated,
    onSecondaryContainer = GoldLight,
    tertiary = SkyBlue,
    onTertiary = Color.White,
    background = RoyalBlueDeep,
    onBackground = CreamBackground,
    surface = RoyalBlueDark,
    onSurface = CreamBackground,
    surfaceVariant = RoyalBlueCard,
    onSurfaceVariant = GoldLight,
    outline = RoyalBlueBorder,
    error = CrimsonRed,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = GoldDark,
    onPrimary = Color.White,
    primaryContainer = GoldLight,
    onPrimaryContainer = RoyalBlueDeep,
    secondary = GoldPrimary,
    onSecondary = RoyalBlueDeep,
    secondaryContainer = CreamSurface,
    onSecondaryContainer = RoyalBlueDeep,
    tertiary = SkyBlue,
    onTertiary = Color.White,
    background = CreamBackground,
    onBackground = RoyalBlueDeep,
    surface = CreamSurface,
    onSurface = RoyalBlueDeep,
    surfaceVariant = CreamCard,
    onSurfaceVariant = RoyalBlueDeep,
    outline = RoyalBlueBorder.copy(alpha = 0.3f),
    error = CrimsonRed,
    onError = Color.White
)

@Composable
fun AlJazariTheme(
    darkTheme: Boolean = true, // Default to Islamic luxury dark mode
    activeFontFamily: FontFamily = CairoFontFamily,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val typography = createAppTypography(activeFontFamily)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
