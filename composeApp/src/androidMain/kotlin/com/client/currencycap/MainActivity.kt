package com.client.currencycap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.core.context.GlobalContext.stopKoin
import ui.App
import ui.theme.AppM3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // TODO: Add dark/light support
            // should also change BlurDarkBackground to BlurLightBackground
            AppM3Theme(dark = true) {
                App()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }
}