package com.client.currencycap.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel
import ui.App
import ui.screens.MainState
import ui.screens.MainViewModel
import ui.screens.initial.splash.SplashScreen
import ui.theme.AppM3Theme

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDarkTheme = isSystemInDarkTheme()
            val appState by mainViewModel.appState.collectAsStateWithLifecycle()
            val isLoading = appState is MainState.Loading

            DisposableEffect(isDarkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
                    navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
                )
                onDispose { }
            }

            AppM3Theme(isDarkMode = true) {
                if (isLoading) SplashScreen()
                else App(mainViewModel)
            }
        }
    }
}