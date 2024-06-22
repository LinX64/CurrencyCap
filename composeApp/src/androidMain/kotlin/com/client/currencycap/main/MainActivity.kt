package com.client.currencycap.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinContext
import org.koin.core.context.stopKoin
import ui.App
import ui.navigation.NavRoutes
import ui.theme.AppM3Theme

class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModel<MainViewModel>()
    private var startDestination: (uid: String) -> String = { NavRoutes.LANDING }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.viewState
                    .onEach {
                        when (it) {
                            is MainState.LoggedIn -> {
                                val uid = it.uid
                                startDestination = { NavRoutes.MARKET_OVERVIEW + "/${uid}" }
                            }

                            is MainState.LoggedOut -> startDestination = { NavRoutes.LOGIN }
                            else -> Unit
                        }
                    }
                    .collect {}
            }
        }

        setContent {
            // TODO: Add dark/light support
            KoinContext {
                AppM3Theme(dark = true) {
                    App(
                        startDestination = startDestination,
                    )
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()

        stopKoin()
    }
}