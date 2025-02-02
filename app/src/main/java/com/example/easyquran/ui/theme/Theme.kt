package com.example.easyquran.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = ColorPrimaryDark,
    onPrimary = ColorOnPrimaryDark,
    primaryContainer = ColorPrimaryContainerDark,
    secondary = ColorSecondaryDark,
    onSecondary = ColorOnSecondaryDark,
    secondaryContainer = ColorSecondaryContainerDark,
    surface = ColorSurfaceDark,
    onSurface = ColorOnSurfaceDark,
    surfaceVariant = ColorSurfaceVariantDark,
    onSurfaceVariant = ColorOnSurfaceVariantDark,
    background = ColorBackgroundDark,
    onBackground = ColorOnBackgroundDark
)

private val LightColorScheme = lightColorScheme(
    primary = ColorPrimaryLight,
    onPrimary = ColorOnPrimaryLight,
    primaryContainer = ColorPrimaryContainerLight,
    secondary = ColorSecondaryLight,
    secondaryContainer = ColorSecondaryContainerLight,
    onSecondary = ColorOnSecondaryLight,
    surface = ColorSurfaceLight,
    surfaceVariant = ColorSurfaceVariantLight,
    onSurface = ColorOnSurfaceLight,
    onSurfaceVariant = ColorOnSurfaceVariantLight,
    background = ColorBackgroundLight,
    onBackground = ColorOnBackgroundLight
)

@Composable
fun EasyQuranTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}