package com.client.currencycap.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.toArgb
import ui.App
import ui.theme.AppM3Theme
import ui.theme.ThemeMode

class MainActivity : ComponentActivity() {

    //private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDarkTheme = isSystemInDarkTheme()
            val statusBarBackgroundColor = MaterialTheme.colorScheme.surface.toArgb()

            DisposableEffect(isDarkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = if (isDarkTheme) SystemBarStyle.dark(
                        scrim = statusBarBackgroundColor
                    ) else SystemBarStyle.light(
                        scrim = statusBarBackgroundColor,
                        darkScrim = statusBarBackgroundColor
                    ),
                    navigationBarStyle = SystemBarStyle.dark(
                        scrim = Color.TRANSPARENT
                    )
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