package com.client.currencycap

import android.app.Application
import com.google.firebase.FirebaseApp
import di.dataStoreModule
import di.httpClientModule
import di.repositoryModule
import di.useCaseModule
import di.viewModelModule
import org.koin.core.context.startKoin
import util.ApplicationComponent

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                listOf(
                    httpClientModule,
                    repositoryModule,
                    viewModelModule,
                    useCaseModule,
                    dataStoreModule,
                    viewModelModule
                )
            )
        }

        FirebaseApp.initializeApp(this)
        ApplicationComponent.init()
    }
}
