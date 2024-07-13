package com.client.currencycap

import androidx.compose.runtime.Composable
import di.dataModule
import di.dataStoreModule
import di.httpClientModule
import di.previewModule
import di.repositoryModule
import di.useCaseModule
import di.viewModelModule
import org.koin.compose.KoinApplication
import org.koin.mp.KoinPlatformTools
import ui.theme.AppM3Theme

@Composable
internal fun KoinPreview(
    content: @Composable () -> Unit
) {
    if (KoinPlatformTools.defaultContext().getOrNull() == null) {
        KoinApplication(application = {

            modules(
                httpClientModule,
                repositoryModule,
                viewModelModule,
                useCaseModule,
                dataStoreModule,
                viewModelModule,
                dataModule,
                previewModule
            )
        }) {
            AppM3Theme(dark = false) {
                content()
            }
        }
    } else {
        content()
    }
}