package com.client.currencycap.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.client.currencycap.R
import java.text.DecimalFormat

@Composable
actual fun getPlaceHolderDrawable(): Painter {
    return painterResource(id = R.drawable.baseline_monetization_on_24)
}

@Composable
actual fun getArrowBottomLeftDrawable(): Painter {
    return painterResource(id = R.drawable.ic_arrow_bottom_left)
}

@Composable
actual fun getBtcIcon(): Int {
    return R.drawable.ic_btc
}

@Composable
actual fun getEthIcon(): Int {
    return R.drawable.ic_ethereum
}

@Composable
actual fun getSearchText(): String {
    return stringResource(id = R.string.search)
}

actual fun formatCurrentTotal(currentTotal: Long): String {
    val decimalFormat = DecimalFormat("$#,###")
    return decimalFormat.format(currentTotal)
}

@Composable
actual fun getIcon(icon: Int): Painter {
    return painterResource(id = icon)
}