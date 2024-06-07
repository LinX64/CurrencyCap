package ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.baseline_monetization_on_24
import currencycap.composeapp.generated.resources.baseline_trending_down_24
import currencycap.composeapp.generated.resources.baseline_trending_up_24
import currencycap.composeapp.generated.resources.ic_btc
import currencycap.composeapp.generated.resources.ic_ethereum
import org.jetbrains.compose.resources.painterResource

@Composable
actual fun getTrendingUpIcon(): Painter {
    return painterResource(Res.drawable.baseline_trending_up_24)
}

@Composable
actual fun getTrendingDownIcon(): Painter {
    return painterResource(Res.drawable.baseline_trending_down_24)
}

@Composable
actual fun getBtcIcon(): Painter {
    return painterResource(Res.drawable.ic_btc)
}

@Composable
actual fun getEthIcon(): Painter {
    return painterResource(Res.drawable.ic_ethereum)
}

@Composable
actual fun getPlaceHolderDrawable(): Painter {
    return painterResource(Res.drawable.baseline_monetization_on_24)
}

@Composable
actual fun getSearchText(): String {
    return "Search"
}

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