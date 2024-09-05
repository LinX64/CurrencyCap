package com.client.currencycap.main

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel
import ui.App
import ui.screens.MainViewModel
import ui.theme.AppM3Theme

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setOnExitAnimationListener { splashScreenView ->
            val fadeOut = ObjectAnimator.ofFloat(splashScreenView.view, View.ALPHA, 1f, 0f)
            with(fadeOut) {
                interpolator = AnticipateInterpolator()
                duration = 800L
                start()
                doOnEnd { splashScreenView.remove() }
            }
        }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.BLACK)
        )

        setContent {
            val isDarkMode by mainViewModel.isDark.collectAsStateWithLifecycle()
            AppM3Theme(isDarkMode = isDarkMode) {
                App()
            }
        }
    }
}