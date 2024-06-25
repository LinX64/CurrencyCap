package com.client.currencycap

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import di.dataStoreModule
import di.httpClientModule
import di.previewModule
import di.repositoryModule
import di.useCaseModule
import di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.mp.KoinPlatformTools
import ui.components.BlurBackground
import ui.theme.AppM3Theme

@Composable
internal fun KoinPreview(
    content: @Composable () -> Unit
) {
    if (KoinPlatformTools.defaultContext().getOrNull() == null) {
        val context = LocalContext.current
        KoinApplication(application = {
            androidContext(context)

            modules(
                httpClientModule,
                repositoryModule,
                viewModelModule,
                useCaseModule,
                dataStoreModule,
                viewModelModule,
                previewModule,
            )
        }) {
            AppM3Theme(dark = true) {
                BlurBackground {
                    content()
                }
            }
        }
    }
}