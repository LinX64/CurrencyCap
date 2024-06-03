package com.client.currencycap.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

@Composable
expect fun getPlaceHolderDrawable(): Painter

@Composable
expect fun getSearchText(): String