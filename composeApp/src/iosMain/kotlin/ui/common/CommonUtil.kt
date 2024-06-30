package ui.common

import androidx.compose.runtime.Composable
import di.SETTINGS_PREFERENCES
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import platform.Foundation.NSFileManager
import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.currentLocale
import platform.Foundation.localeIdentifier

actual fun formatToPrice(price: Double): String {
    return "$price"
//    val formatter = NSNumberFormatter()
//    formatter.minimumFractionDigits = 5u
//    formatter.maximumFractionDigits = 5u
//    formatter.numberStyle = NSNumberFormatterDecimalStyle
//    return formatter.stringFromNumber(NSNumber()) ?: "$price"
}

actual fun formatCurrentTotal(currentTotal: Long): String {
//    val formatter = NSNumberFormatter()
//    formatter.minimumFractionDigits = 5u
//    formatter.maximumFractionDigits = 5u
//    formatter.numberStyle = NSNumberFormatterDecimalStyle
//    return formatter.stringFromNumber(NSNumber()) ?: "$currentTotal"
    return "$currentTotal"
}

actual fun String.getCountryName(): String {
    return NSLocale.currentLocale.localeIdentifier.split("_")[1]
}

actual fun String.getCountryFlag(): String {
    return ""
    // TODO: Implement this
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

@OptIn(ExperimentalForeignApi::class)
actual fun Double.formatDecimal(maxFractionDigits: Int): String =
    NSNumberFormatter().apply {
        minimumFractionDigits = 0u
        maximumFractionDigits = maxFractionDigits.convert()
        numberStyle = NSNumberFormatterDecimalStyle
    }.stringFromNumber(number = NSNumber(double = this)) ?: ""