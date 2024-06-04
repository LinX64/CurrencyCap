package com.client.currencycap.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import di.httpClientModule
import di.repositoryModule
import org.koin.compose.KoinApplication
import ui.App
import ui.theme.AppM3Theme
import viewModelModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            KoinApplication(
                application = {
                    modules(viewModelModule, repositoryModule, httpClientModule)
                }
            ) {
                // TODO: Add dark mode support
                AppM3Theme(dark = true) {
                    App()
                }
            }
        }
    }
}