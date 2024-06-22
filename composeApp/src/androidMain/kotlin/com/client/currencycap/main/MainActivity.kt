package com.client.currencycap.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin()

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

    private fun startKoin() {
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
    }

    override fun onStop() {
        super.onStop()

        stopKoin()
    }
}