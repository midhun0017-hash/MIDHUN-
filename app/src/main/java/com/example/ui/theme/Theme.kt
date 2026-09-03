package com.example.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class AppThemeMode(
    val title: String,
    val subtitle: String,
    val previewPrimary: Color,
    val previewBg: Color
) {
    MIDNIGHT(
        title = "Midnight Obsidian",
        subtitle = "Deep dark mode with luminous teal accents",
        previewPrimary = MidnightPrimary,
        previewBg = MidnightBackground
    ),
    NORDIC_SAGE(
        title = "Nordic Sage",
        subtitle = "Botanical sage green and clean light paper",
        previewPrimary = NordicPrimary,
        previewBg = NordicBackground
    ),
    WARM_LINEN(
        title = "Warm Linen",
        subtitle = "Cozy parchment with amber editorial accents",
        previewPrimary = WarmPrimary,
        previewBg = WarmBackground
    ),
    OCEAN_SLATE(
        title = "Ocean Slate",
        subtitle = "Cool tech palette with vivid sky blue highlights",
        previewPrimary = OceanPrimary,
        previewBg = OceanBackground
    )
}

val MidnightColorScheme: ColorScheme = darkColorScheme(
    primary = MidnightPrimary,
    onPrimary = MidnightOnPrimary,
    primaryContainer = MidnightPrimaryContainer,
    onPrimaryContainer = MidnightOnPrimaryContainer,
    secondary = MidnightSecondary,
    onSecondary = MidnightOnSecondary,
    secondaryContainer = MidnightSecondaryContainer,
    onSecondaryContainer = MidnightOnSecondaryContainer,
    background = MidnightBackground,
    onBackground = MidnightOnBackground,
    surface = MidnightSurface,
    onSurface = MidnightOnSurface,
    surfaceVariant = MidnightSurfaceVariant,
    onSurfaceVariant = MidnightOnSurfaceVariant,
    outline = MidnightOutline
)

val NordicColorScheme: ColorScheme = lightColorScheme(
    primary = NordicPrimary,
    onPrimary = NordicOnPrimary,
    primaryContainer = NordicPrimaryContainer,
    onPrimaryContainer = NordicOnPrimaryContainer,
    secondary = NordicSecondary,
    onSecondary = NordicOnSecondary,
    secondaryContainer = NordicSecondaryContainer,
    onSecondaryContainer = NordicOnSecondaryContainer,
    background = NordicBackground,
    onBackground = NordicOnBackground,
    surface = NordicSurface,
    onSurface = NordicOnSurface,
    surfaceVariant = NordicSurfaceVariant,
    onSurfaceVariant = NordicOnSurfaceVariant,
    outline = NordicOutline
)

val WarmColorScheme: ColorScheme = lightColorScheme(
    primary = WarmPrimary,
    onPrimary = WarmOnPrimary,
    primaryContainer = WarmPrimaryContainer,
    onPrimaryContainer = WarmOnPrimaryContainer,
    secondary = WarmSecondary,
    onSecondary = WarmOnSecondary,
    secondaryContainer = WarmSecondaryContainer,
    onSecondaryContainer = WarmOnSecondaryContainer,
    background = WarmBackground,
    onBackground = WarmOnBackground,
    surface = WarmSurface,
    onSurface = WarmOnSurface,
    surfaceVariant = WarmSurfaceVariant,
    onSurfaceVariant = WarmOnSurfaceVariant,
    outline = WarmOutline
)

val OceanColorScheme: ColorScheme = lightColorScheme(
    primary = OceanPrimary,
    onPrimary = OceanOnPrimary,
    primaryContainer = OceanPrimaryContainer,
    onPrimaryContainer = OceanOnPrimaryContainer,
    secondary = OceanSecondary,
    onSecondary = OceanOnSecondary,
    secondaryContainer = OceanSecondaryContainer,
    onSecondaryContainer = OceanOnSecondaryContainer,
    background = OceanBackground,
    onBackground = OceanOnBackground,
    surface = OceanSurface,
    onSurface = OceanOnSurface,
    surfaceVariant = OceanSurfaceVariant,
    onSurfaceVariant = OceanOnSurfaceVariant,
    outline = OceanOutline
)

fun getThemeColorScheme(themeMode: AppThemeMode): ColorScheme {
    return when (themeMode) {
        AppThemeMode.MIDNIGHT -> MidnightColorScheme
        AppThemeMode.NORDIC_SAGE -> NordicColorScheme
        AppThemeMode.WARM_LINEN -> WarmColorScheme
        AppThemeMode.OCEAN_SLATE -> OceanColorScheme
    }
}

@Composable
fun MyApplicationTheme(
    themeMode: AppThemeMode = AppThemeMode.MIDNIGHT,
    content: @Composable () -> Unit,
) {
    val colorScheme = getThemeColorScheme(themeMode)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
