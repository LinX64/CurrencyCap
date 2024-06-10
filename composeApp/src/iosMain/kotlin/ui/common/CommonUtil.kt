package ui.common

import platform.Foundation.NSLocale
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