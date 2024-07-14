package ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.raleway_black
import currencycap.composeapp.generated.resources.raleway_bold
import currencycap.composeapp.generated.resources.raleway_extrabold
import currencycap.composeapp.generated.resources.raleway_light
import currencycap.composeapp.generated.resources.raleway_medium
import currencycap.composeapp.generated.resources.raleway_regular
import currencycap.composeapp.generated.resources.raleway_semibold
import org.jetbrains.compose.resources.Font

@Composable
fun raleWay() = FontFamily(
    Font(Res.font.raleway_regular, FontWeight.Normal),
    Font(Res.font.raleway_medium, FontWeight.Medium),
    Font(Res.font.raleway_black, FontWeight.Black),
    Font(Res.font.raleway_light, FontWeight.Light),
    Font(Res.font.raleway_semibold, FontWeight.SemiBold),
    Font(Res.font.raleway_bold, FontWeight.Bold),
    Font(Res.font.raleway_extrabold, FontWeight.ExtraBold),
)

@Composable
fun CurrencyTypography() = Typography().run {
    val raleWay = raleWay()

    copy(
        displayLarge = displayLarge.copy(fontFamily = raleWay),
        displayMedium = displayMedium.copy(fontFamily = raleWay),
        displaySmall = displaySmall.copy(fontFamily = raleWay),
        headlineLarge = headlineLarge.copy(fontFamily = raleWay),
        headlineMedium = headlineMedium.copy(fontFamily = raleWay),
        headlineSmall = headlineSmall.copy(fontFamily = raleWay),
        titleLarge = titleLarge.copy(fontFamily = raleWay),
        titleMedium = titleMedium.copy(fontFamily = raleWay),
        titleSmall = titleSmall.copy(fontFamily = raleWay),
        bodyLarge = bodyLarge.copy(fontFamily = raleWay),
        bodyMedium = bodyMedium.copy(fontFamily = raleWay),
        bodySmall = bodySmall.copy(fontFamily = raleWay),
        labelLarge = labelLarge.copy(fontFamily = raleWay),
        labelMedium = labelMedium.copy(fontFamily = raleWay),
        labelSmall = labelSmall.copy(fontFamily = raleWay)
    )
}

