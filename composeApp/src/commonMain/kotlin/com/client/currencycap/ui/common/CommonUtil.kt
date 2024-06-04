package com.client.currencycap.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

@Composable
expect fun getPlaceHolderDrawable(): Painter

@Composable
expect fun getArrowBottomLeftDrawable(): Painter

@Composable
expect fun getBtcIcon(): Int

@Composable
expect fun getEthIcon(): Int

@Composable
expect fun getSearchText(): String

expect fun formatCurrentTotal(currentTotal: Long): String

@Composable
expect fun getIcon(icon: Int): Painter

expect fun formatToPrice(price: Double): String