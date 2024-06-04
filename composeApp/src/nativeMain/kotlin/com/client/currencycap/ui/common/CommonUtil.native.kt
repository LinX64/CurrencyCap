package com.client.currencycap.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import platform.Foundation.NSDecimalNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle

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