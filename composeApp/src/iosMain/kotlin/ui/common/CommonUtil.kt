package ui.common

import androidx.compose.runtime.Composable
import di.SETTINGS_PREFERENCES
import platform.Foundation.NSFileManager
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle

actual fun formatToPrice(price: Double): String {
    val formatter = NSNumberFormatter()
    formatter.minimumFractionDigits = 5u
    formatter.maximumFractionDigits = 5u
    formatter.numberStyle = NSNumberFormatterDecimalStyle
    return formatter.stringFromNumber(NSNumber(double = price)) ?: price.toString()
}

actual fun formatCurrentTotal(currentTotal: Long): String {
    val formatter = NSNumberFormatter()
    formatter.minimumFractionDigits = 5u
    formatter.maximumFractionDigits = 5u
    formatter.numberStyle = NSNumberFormatterDecimalStyle
    return formatter.stringFromNumber(NSNumber()) ?: "$currentTotal"
}

actual fun getSettingsPreferencesPath(): String {
    return NSFileManager.defaultManager.currentDirectoryPath + "/" + SETTINGS_PREFERENCES
}

actual class DecimalFormat {
    actual fun format(double: Double): String {
        val formatter = NSNumberFormatter()
        formatter.minimumFractionDigits = 0u
        formatter.maximumFractionDigits = 2u
        formatter.numberStyle = 1u //Decimal
        return formatter.stringFromNumber(NSNumber(double))!!
    }
}

@Composable
actual fun SendMail(to: String, subject: String) {
    // TODO
}