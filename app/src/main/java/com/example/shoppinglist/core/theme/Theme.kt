package com.example.shoppinglist.core.theme

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
    background = AppColors.BackgroundDark,
    surface = AppColors.SurfaceDark,
    onSurface = AppColors.OnSurfaceDark,
    onSurfaceVariant = AppColors.OnSurfaceVariantDark,
    primaryContainer = AppColors.PrimaryContainerDark,
    onPrimaryContainer = AppColors.OnPrimaryContainerDark,
    surfaceContainerHigh = AppColors.SurfaceContainerHighDark,
    secondary = AppColors.SecondaryDark,
    primary = AppColors.PrimaryDark,
    secondaryContainer = AppColors.SecondaryContainerDark,
    onSecondaryContainer = AppColors.OnSecondaryContainerDark,
    onPrimary = AppColors.OnPrimaryDark,
    surfaceContainerHighest = AppColors.SurfaceContainerHighestDark,
    outlineVariant = AppColors.OutlineVariantDark,
    surfaceContainerLow = AppColors.SurfaceContainerLowDark,
)

private val LightColorScheme = lightColorScheme(
    background = AppColors.BackgroundLight,
    surface = AppColors.SurfaceLight,
    onSurface = AppColors.OnSurfaceLight,
    onSurfaceVariant = AppColors.OnSurfaceVariantLight,
    primaryContainer = AppColors.PrimaryContainerLight,
    onPrimaryContainer = AppColors.OnPrimaryContainerLight,
    surfaceContainerHigh = AppColors.SurfaceContainerHighLight,
    primary = AppColors.PrimaryLight,
    secondaryContainer = AppColors.SecondaryContainerLight,
    onSecondaryContainer = AppColors.OnSecondaryContainerLight,
    onPrimary = AppColors.OnPrimaryLight,
    surfaceContainerHighest = AppColors.SurfaceContainerHighestLight,
    outlineVariant = AppColors.OutlineVariantLight,
    surfaceContainerLow = AppColors.SurfaceContainerLowLight,
)

@Composable
fun ShoppingListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
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