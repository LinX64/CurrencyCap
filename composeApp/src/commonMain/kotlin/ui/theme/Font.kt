package ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.titillium_web_bold
import currencycap.composeapp.generated.resources.titillium_web_bold_italic
import currencycap.composeapp.generated.resources.titillium_web_italic
import currencycap.composeapp.generated.resources.titillium_web_medium
import currencycap.composeapp.generated.resources.titillium_web_medium_italic
import currencycap.composeapp.generated.resources.titillium_web_regular
import org.jetbrains.compose.resources.Font

@Composable
fun TitilliumWebFamily() = FontFamily(
    Font(Res.font.titillium_web_regular),
    Font(Res.font.titillium_web_italic, style = FontStyle.Italic),
    Font(Res.font.titillium_web_medium, FontWeight.Medium),
    Font(Res.font.titillium_web_medium_italic, FontWeight.Medium, style = FontStyle.Italic),
    Font(Res.font.titillium_web_bold, FontWeight.Bold),
    Font(Res.font.titillium_web_bold_italic, FontWeight.Bold, style = FontStyle.Italic)
)

@Composable
fun TitilliumWebTypography() = Typography().run {
    val fontFamily = TitilliumWebFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}
