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
import ui.theme.ThemeMode

class MainActivity : ComponentActivity() {

    //private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDarkTheme = isSystemInDarkTheme()
            val context = LocalContext.current as ComponentActivity

            DisposableEffect(isDarkTheme) {
                context.enableEdgeToEdge(
                    statusBarStyle = if (isDarkTheme)
                        SystemBarStyle.dark(Color.TRANSPARENT)
                    else SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
                )
                onDispose { }
            }

            // TODO: Add dark/light support
            AppM3Theme(themeMode = ThemeMode.DARK) {
                App()
            }
        }
    }
}