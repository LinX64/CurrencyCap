package com.client.currencycap.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import di.httpClientModule
import di.repositoryModule
import org.koin.compose.KoinApplication
import ui.App
import ui.theme.AppM3Theme
import viewModelModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KoinApplication(
                application = {
                    modules(viewModelModule, repositoryModule, httpClientModule)
                }
            ) {
                AppM3Theme(dark = true) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        App()
                    }
                }
            }
        }
    }
}