package com.client.currencycap.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.client.currencycap.R

@Composable
actual fun getPlaceHolderDrawable(): Painter {
    return painterResource(id = R.drawable.baseline_monetization_on_24)
}