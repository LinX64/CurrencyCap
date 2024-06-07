package ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

@Composable
expect fun getPlaceHolderDrawable(): Painter

@Composable
expect fun getTrendingUpIcon(): Painter

@Composable
expect fun getTrendingDownIcon(): Painter

@Composable
expect fun getBtcIcon(): Painter

@Composable
expect fun getEthIcon(): Painter

@Composable
expect fun getSearchText(): String

expect fun formatCurrentTotal(currentTotal: Long): String
expect fun formatToPrice(price: Double): String