package ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import ui.theme.colors.CurrencyColors

@Composable
fun AppM3Theme(
    dark: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (dark) appDarkColorScheme() else appLightColorScheme(),
        //typography = TitilliumWebTypography(),
        content = content
    )
}

private fun appLightColorScheme(): ColorScheme = ColorScheme(
    primary = CurrencyColors.Green.primary,
    onPrimary = CurrencyColors.White,
    primaryContainer = CurrencyColors.Blue.light,
    onPrimaryContainer = CurrencyColors.White,
    inversePrimary = CurrencyColors.Blue.dark,
    secondary = CurrencyColors.Green.primary,
    onSecondary = CurrencyColors.White,
    secondaryContainer = CurrencyColors.Green.light,
    onSecondaryContainer = CurrencyColors.White,
    tertiary = CurrencyColors.Green.primary,
    onTertiary = CurrencyColors.White,
    tertiaryContainer = CurrencyColors.Green.light,
    onTertiaryContainer = CurrencyColors.White,

    error = CurrencyColors.Red.primary,
    onError = CurrencyColors.White,
    errorContainer = CurrencyColors.Red.light,
    onErrorContainer = CurrencyColors.White,

    background = CurrencyColors.White,
    onBackground = CurrencyColors.Black,
    surface = CurrencyColors.White,
    onSurface = CurrencyColors.Black,
    surfaceVariant = CurrencyColors.ExtraLightGray,
    onSurfaceVariant = CurrencyColors.Black,
    surfaceTint = CurrencyColors.Black,
    inverseSurface = CurrencyColors.DarkGray,
    inverseOnSurface = CurrencyColors.White,

    outline = CurrencyColors.Gray,
    outlineVariant = CurrencyColors.DarkGray,
    scrim = CurrencyColors.ExtraDarkGray.copy(alpha = 0.8f)
)

private fun appDarkColorScheme(): ColorScheme = ColorScheme(
    primary = CurrencyColors.Lemon.primary,
    onPrimary = CurrencyColors.White,
    primaryContainer = CurrencyColors.Lemon.light,
    onPrimaryContainer = CurrencyColors.White,
    inversePrimary = CurrencyColors.Lemon.dark,
    secondary = CurrencyColors.Green.primary,
    onSecondary = CurrencyColors.White,
    secondaryContainer = CurrencyColors.Green.light,
    onSecondaryContainer = CurrencyColors.White,
    tertiary = CurrencyColors.Green.primary,
    onTertiary = CurrencyColors.White,
    tertiaryContainer = CurrencyColors.Green.light,
    onTertiaryContainer = CurrencyColors.White,

    error = CurrencyColors.Red.primary,
    onError = CurrencyColors.White,
    errorContainer = CurrencyColors.Red.light,
    onErrorContainer = CurrencyColors.White,

    background = CurrencyColors.Black,
    onBackground = CurrencyColors.White,
    surface = CurrencyColors.Black,
    onSurface = CurrencyColors.White,
    surfaceVariant = CurrencyColors.ExtraDarkGray,
    onSurfaceVariant = CurrencyColors.White,
    surfaceTint = CurrencyColors.White,
    inverseSurface = CurrencyColors.LightGray,
    inverseOnSurface = CurrencyColors.Black,

    outline = CurrencyColors.Gray,
    outlineVariant = CurrencyColors.LightGray,
    scrim = CurrencyColors.ExtraLightGray.copy(alpha = 0.8f)
)
