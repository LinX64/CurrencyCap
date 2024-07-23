package ui.theme

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import ui.theme.ThemeMode.DARK
import ui.theme.ThemeMode.LIGHT
import ui.theme.ThemeMode.SYSTEM
import ui.theme.colors.CurrencyColors

@Composable
internal expect fun SystemAppearance(isDark: Boolean)

@Composable
fun AppM3Theme(
    isDarkMode: Boolean,
    content: @Composable () -> Unit
) {
    SystemAppearance(!isDarkMode)

    MaterialTheme(
        colorScheme = if (isDarkMode) appDarkColorScheme() else appLightColorScheme(),
        typography = CurrencyTypography(),
        content = content
    )
}

@VisibleForTesting
@Stable
private fun appLightColorScheme(): ColorScheme = ColorScheme(
    primary = CurrencyColors.Green.primary,
    onPrimary = CurrencyColors.Gray.copy(alpha = 0.9f),
    primaryContainer = CurrencyColors.Blue.light,
    onPrimaryContainer = CurrencyColors.White,
    inversePrimary = CurrencyColors.Muted_Teal.dark,
    secondary = CurrencyColors.Muted_Teal.primary,
    onSecondary = CurrencyColors.White,
    secondaryContainer = CurrencyColors.Green.light,
    onSecondaryContainer = CurrencyColors.White,
    tertiary = CurrencyColors.Green.primary,
    onTertiary = CurrencyColors.White,
    tertiaryContainer = CurrencyColors.Green.light,
    onTertiaryContainer = CurrencyColors.White,
    background = CurrencyColors.White.copy(alpha = 0.4f),
    onBackground = CurrencyColors.Black,
    surface = CurrencyColors.White,
    onSurface = CurrencyColors.Black,
    surfaceVariant = CurrencyColors.ExtraLightGray,
    onSurfaceVariant = CurrencyColors.Black,
    surfaceTint = CurrencyColors.Black,
    inverseSurface = CurrencyColors.DarkGray,
    inverseOnSurface = CurrencyColors.White,
    error = CurrencyColors.Red.primary,
    onError = CurrencyColors.White,
    errorContainer = CurrencyColors.Red.light,
    onErrorContainer = CurrencyColors.White,
    outline = CurrencyColors.Gray,
    outlineVariant = CurrencyColors.DarkGray,
    scrim = CurrencyColors.ExtraDarkGray.copy(alpha = 0.8f),
    surfaceBright = CurrencyColors.White,
    surfaceDim = CurrencyColors.ExtraLightGray,
    surfaceContainer = CurrencyColors.White,
    surfaceContainerHigh = CurrencyColors.ExtraLightGray,
    surfaceContainerHighest = CurrencyColors.LightGray,
    surfaceContainerLow = CurrencyColors.Gray,
    surfaceContainerLowest = CurrencyColors.DarkGray
)

@VisibleForTesting
@Stable
private fun appDarkColorScheme(): ColorScheme = ColorScheme(
    primary = CurrencyColors.Lemon.primary,
    onPrimary = CurrencyColors.LemonGreen,
    primaryContainer = CurrencyColors.Lemon.light,
    onPrimaryContainer = CurrencyColors.White,
    inversePrimary = CurrencyColors.Lemon.dark,
    secondary = CurrencyColors.Muted_Teal.primary,
    onSecondary = CurrencyColors.White,
    secondaryContainer = CurrencyColors.Green.light,
    onSecondaryContainer = CurrencyColors.White,
    tertiary = CurrencyColors.Green.primary,
    onTertiary = CurrencyColors.White,
    tertiaryContainer = CurrencyColors.Green.light,
    onTertiaryContainer = CurrencyColors.White,
    background = CurrencyColors.Black,
    onBackground = CurrencyColors.White,
    surface = CurrencyColors.Black,
    onSurface = CurrencyColors.White,
    surfaceVariant = CurrencyColors.ExtraDarkGray,
    onSurfaceVariant = CurrencyColors.White,
    surfaceTint = CurrencyColors.White,
    inverseSurface = CurrencyColors.LightGray,
    inverseOnSurface = CurrencyColors.Black,
    error = CurrencyColors.Red.primary,
    onError = CurrencyColors.White,
    errorContainer = CurrencyColors.Red.light,
    onErrorContainer = CurrencyColors.White,
    outline = CurrencyColors.Gray,
    outlineVariant = CurrencyColors.LightGray,
    scrim = CurrencyColors.ExtraLightGray.copy(alpha = 0.8f),
    surfaceBright = CurrencyColors.Black,
    surfaceDim = CurrencyColors.ExtraDarkGray,
    surfaceContainer = CurrencyColors.Black,
    surfaceContainerHigh = CurrencyColors.ExtraDarkGray,
    surfaceContainerHighest = CurrencyColors.DarkGray,
    surfaceContainerLow = CurrencyColors.Gray,
    surfaceContainerLowest = CurrencyColors.LightGray
)

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

val LocalThemeMode = staticCompositionLocalOf { SYSTEM }

@Composable
fun isInDarkTheme(): Boolean = when (LocalThemeMode.current) {
    DARK -> true
    LIGHT -> false
    else -> isSystemInDarkTheme()
}