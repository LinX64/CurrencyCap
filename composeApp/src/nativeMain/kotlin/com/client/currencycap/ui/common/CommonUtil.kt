package com.client.currencycap.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_arrow_bottom_left
import currencycap.composeapp.generated.resources.ic_btc
import currencycap.composeapp.generated.resources.ic_ethereum
import org.jetbrains.compose.resources.painterResource
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle

actual fun formatCurrentTotal(currentTotal: Long): String {
    val formatter = NSNumberFormatter()
    formatter.minimumFractionDigits = 5u
    formatter.maximumFractionDigits = 5u
    formatter.numberStyle = NSNumberFormatterDecimalStyle
    return formatter.stringFromNumber(NSNumber()) ?: "$currentTotal"
}

@Composable
actual fun getArrowBottomLeftDrawable(): Painter {
    return painterResource(Res.drawable.ic_arrow_bottom_left)
}

@Composable
actual fun getBtcIcon(): Painter {
    return painterResource(Res.drawable.ic_btc)
}

@Composable
actual fun getEthIcon(): Painter {
    return painterResource(Res.drawable.ic_ethereum)
}

actual fun formatToPrice(price: Double): String {
    val formatter = NSNumberFormatter()
    formatter.minimumFractionDigits = 5u
    formatter.maximumFractionDigits = 5u
    formatter.numberStyle = NSNumberFormatterDecimalStyle
    return formatter.stringFromNumber(NSNumber()) ?: "$price"
}

