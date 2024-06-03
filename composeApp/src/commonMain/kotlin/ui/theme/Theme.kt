package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import presentation.ui.backgroundDark
import presentation.ui.backgroundLight
import presentation.ui.errorContainerDark
import presentation.ui.errorContainerLight
import presentation.ui.errorDark
import presentation.ui.errorLight
import presentation.ui.inverseOnSurfaceDark
import presentation.ui.inverseOnSurfaceLight
import presentation.ui.inversePrimaryDark
import presentation.ui.inversePrimaryLight
import presentation.ui.inverseSurfaceDark
import presentation.ui.inverseSurfaceLight
import presentation.ui.onBackgroundDark
import presentation.ui.onBackgroundLight
import presentation.ui.onErrorContainerDark
import presentation.ui.onErrorContainerLight
import presentation.ui.onErrorDark
import presentation.ui.onErrorLight
import presentation.ui.onPrimaryContainerDark
import presentation.ui.onPrimaryContainerLight
import presentation.ui.onPrimaryDark
import presentation.ui.onPrimaryLight
import presentation.ui.onSecondaryContainerDark
import presentation.ui.onSecondaryContainerLight
import presentation.ui.onSecondaryDark
import presentation.ui.onSecondaryLight
import presentation.ui.onSurfaceDark
import presentation.ui.onSurfaceLight
import presentation.ui.onSurfaceVariantDark
import presentation.ui.onSurfaceVariantLight
import presentation.ui.onTertiaryContainerDark
import presentation.ui.onTertiaryContainerLight
import presentation.ui.onTertiaryDark
import presentation.ui.onTertiaryLight
import presentation.ui.outlineDark
import presentation.ui.outlineLight
import presentation.ui.outlineVariantDark
import presentation.ui.outlineVariantLight
import presentation.ui.primaryContainerDark
import presentation.ui.primaryContainerLight
import presentation.ui.primaryDark
import presentation.ui.primaryLight
import presentation.ui.scrimDark
import presentation.ui.scrimLight
import presentation.ui.secondaryContainerDark
import presentation.ui.secondaryContainerLight
import presentation.ui.secondaryDark
import presentation.ui.secondaryLight
import presentation.ui.surfaceBrightDark
import presentation.ui.surfaceBrightLight
import presentation.ui.surfaceContainerDark
import presentation.ui.surfaceContainerHighDark
import presentation.ui.surfaceContainerHighLight
import presentation.ui.surfaceContainerHighestDark
import presentation.ui.surfaceContainerHighestLight
import presentation.ui.surfaceContainerLight
import presentation.ui.surfaceContainerLowDark
import presentation.ui.surfaceContainerLowLight
import presentation.ui.surfaceContainerLowestDark
import presentation.ui.surfaceContainerLowestLight
import presentation.ui.surfaceDark
import presentation.ui.surfaceDimDark
import presentation.ui.surfaceDimLight
import presentation.ui.surfaceLight
import presentation.ui.surfaceVariantDark
import presentation.ui.surfaceVariantLight
import presentation.ui.tertiaryContainerDark
import presentation.ui.tertiaryContainerLight
import presentation.ui.tertiaryDark
import presentation.ui.tertiaryLight

private val lightScheme =
    lightColorScheme(
        primary = primaryLight,
        onPrimary = onPrimaryLight,
        primaryContainer = primaryContainerLight,
        onPrimaryContainer = onPrimaryContainerLight,
        secondary = secondaryLight,
        onSecondary = onSecondaryLight,
        secondaryContainer = secondaryContainerLight,
        onSecondaryContainer = onSecondaryContainerLight,
        tertiary = tertiaryLight,
        onTertiary = onTertiaryLight,
        tertiaryContainer = tertiaryContainerLight,
        onTertiaryContainer = onTertiaryContainerLight,
        error = errorLight,
        onError = onErrorLight,
        errorContainer = errorContainerLight,
        onErrorContainer = onErrorContainerLight,
        background = backgroundLight,
        onBackground = onBackgroundLight,
        surface = surfaceLight,
        onSurface = onSurfaceLight,
        surfaceVariant = surfaceVariantLight,
        onSurfaceVariant = onSurfaceVariantLight,
        outline = outlineLight,
        outlineVariant = outlineVariantLight,
        scrim = scrimLight,
        inverseSurface = inverseSurfaceLight,
        inverseOnSurface = inverseOnSurfaceLight,
        inversePrimary = inversePrimaryLight,
        surfaceDim = surfaceDimLight,
        surfaceBright = surfaceBrightLight,
        surfaceContainerLowest = surfaceContainerLowestLight,
        surfaceContainerLow = surfaceContainerLowLight,
        surfaceContainer = surfaceContainerLight,
        surfaceContainerHigh = surfaceContainerHighLight,
        surfaceContainerHighest = surfaceContainerHighestLight,
    )

private val darkScheme =
    darkColorScheme(
        primary = primaryDark,
        onPrimary = onPrimaryDark,
        primaryContainer = primaryContainerDark,
        onPrimaryContainer = onPrimaryContainerDark,
        secondary = secondaryDark,
        onSecondary = onSecondaryDark,
        secondaryContainer = secondaryContainerDark,
        onSecondaryContainer = onSecondaryContainerDark,
        tertiary = tertiaryDark,
        onTertiary = onTertiaryDark,
        tertiaryContainer = tertiaryContainerDark,
        onTertiaryContainer = onTertiaryContainerDark,
        error = errorDark,
        onError = onErrorDark,
        errorContainer = errorContainerDark,
        onErrorContainer = onErrorContainerDark,
        background = backgroundDark,
        onBackground = onBackgroundDark,
        surface = surfaceDark,
        onSurface = onSurfaceDark,
        surfaceVariant = surfaceVariantDark,
        onSurfaceVariant = onSurfaceVariantDark,
        outline = outlineDark,
        outlineVariant = outlineVariantDark,
        scrim = scrimDark,
        inverseSurface = inverseSurfaceDark,
        inverseOnSurface = inverseOnSurfaceDark,
        inversePrimary = inversePrimaryDark,
        surfaceDim = surfaceDimDark,
        surfaceBright = surfaceBrightDark,
        surfaceContainerLowest = surfaceContainerLowestDark,
        surfaceContainerLow = surfaceContainerLowDark,
        surfaceContainer = surfaceContainerDark,
        surfaceContainerHigh = surfaceContainerHighDark,
        surfaceContainerHighest = surfaceContainerHighestDark,
    )

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit,
) {
    val colorScheme =
        when {
            darkTheme -> darkScheme
            else -> lightScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
