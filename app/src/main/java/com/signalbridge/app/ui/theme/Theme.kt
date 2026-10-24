package com.signalbridge.app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.signalbridge.app.domain.model.ThemeMode
import androidx.compose.ui.graphics.Color

val Shape = Shapes.copy(
    small = 4.dp,
    medium = 12.dp,
    large = 20.dp,
    extraLarge = 28.dp
)

private val DarkColorScheme = darkColorScheme(
    primary = BrandColors.Primary,
    primaryContainer = BrandColors.PrimaryContainer,
    secondary = BrandColors.Primary,
    secondaryContainer = Color(0xFF2C365C),
    background = BrandColors.SurfaceBackground,
    surface = BrandColors.SurfaceContainer,
    surfaceVariant = BrandColors.SurfaceContainerHigh,
    error = BrandColors.Error,
    errorContainer = BrandColors.ErrorContainer,
    inverseSurface = BrandColors.SurfaceInverse,
    onPrimary = BrandColors.OnPrimary,
    onSecondary = BrandColors.OnPrimary,
    onBackground = BrandColors.OnSurface,
    onSurface = BrandColors.OnSurface,
    onSurfaceVariant = BrandColors.OnSurfaceVariant,
    onError = BrandColors.OnError,
    outline = BrandColors.Outline,
    surfaceDisabled = Color(0xFF121933),
    onSurfaceDisabled = BrandColors.TextDisabled
)

private val LightColorScheme = lightColorScheme(
    primary = BrandColors.Primary,
    primaryContainer = BrandColors.PrimaryFixed,
    background = Color(0xFFFAFBFE),
    surface = Color(0xFFFFFFFF),
    error = BrandColors.Error,
    onPrimary = Color(0xFFFFFFFF),
    onSurface = Color(0xFF121933),
    outline = BrandColors.Outline
)

@Composable
fun SignalBridgeTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(
            displayLarge = BrandTypography.Display,
            headlineMedium = BrandTypography.Headline,
            titleLarge = BrandTypography.Title,
            bodyLarge = BrandTypography.BodyLarge,
            bodyMedium = BrandTypography.Body,
            labelMedium = BrandTypography.Supporting,
            caption = BrandTypography.Caption
        ),
        shapes = Shape,
        content = content
    )
}

/** Convenience: read whether the current theme is dark. */
@Composable
fun isDarkTheme(): Boolean = MaterialTheme.colorScheme.isDark
