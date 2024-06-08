package com.client.currencycap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import di.httpClientModule
import di.repositoryModule
import di.useCaseModule
import di.viewModelModule
import org.koin.compose.KoinContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import ui.App
import ui.theme.AppM3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            modules(
                listOf(
                    httpClientModule,
                    repositoryModule,
                    viewModelModule,
                    useCaseModule
                )
            )
        }

        enableEdgeToEdge()

        setContent {
            // TODO: Add dark/light support
            // should also change BlurDarkBackground to BlurLightBackground
            KoinContext {
                AppM3Theme(dark = true) {
                    App()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()

        stopKoin()
    }
}