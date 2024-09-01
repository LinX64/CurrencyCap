package ui.common

import androidx.compose.runtime.Composable

expect fun formatCurrentTotal(currentTotal: Long): String
expect fun formatToPrice(price: Double): String
expect fun getSettingsPreferencesPath(): String
expect class DecimalFormat() {
    fun format(double: Double): String
}

@Composable
expect fun SendMail(to: String, subject: String)

