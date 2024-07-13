package com.client.currencycap.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import ui.App
import ui.theme.AppM3Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val statusBarLight = Color.BLACK
            val isDarkMode = isSystemInDarkTheme()
            val context = LocalContext.current as ComponentActivity

            DisposableEffect(isDarkMode) {
                context.enableEdgeToEdge(
                    statusBarStyle = if (!isDarkMode) {
                        SystemBarStyle.dark(scrim = statusBarLight)
                    } else SystemBarStyle.dark(statusBarLight)
                )
                onDispose { }
            }

            // TODO: Add dark/light support
            AppM3Theme(dark = true) {
                App()
            }
        }
    }
}