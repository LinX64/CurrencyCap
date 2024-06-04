package com.client.currencycap.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import platform.Foundation.NSDecimalNumber
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.NSNumberFormatterDecimalStyle

actual fun formatCurrentTotal(currentTotal: Long): String {
    val decimalFormat = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterCurrencyStyle
        maximumFractionDigits = 0u
    }
    val number = NSDecimalNumber(currentTotal)
    return decimalFormat.stringFromNumber(number) ?: "$currentTotal"
}

@Composable
actual fun getArrowBottomLeftDrawable(): Painter {
    TODO("Not yet implemented")
}

@Composable
actual fun getBtcIcon(): Int {
    TODO("Not yet implemented")
}

@Composable
actual fun getEthIcon(): Int {
    TODO("Not yet implemented")
}

@Composable
actual fun getIcon(icon: Int): Painter {
    TODO("Not yet implemented")
}

actual fun formatToPrice(price: Double): String {
    val formatter = NSNumberFormatter()
    formatter.minimumFractionDigits = 5u
    formatter.maximumFractionDigits = 5u
    formatter.numberStyle = NSNumberFormatterDecimalStyle
    return formatter.stringFromNumber(NSNumber()) ?: "$price"
}

