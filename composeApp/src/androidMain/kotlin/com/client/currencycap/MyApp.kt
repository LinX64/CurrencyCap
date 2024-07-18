package com.client.currencycap

import android.app.Application
import com.google.firebase.FirebaseApp
import ui.common.ContextProvider
import ui.common.KoinInitializer
import util.ApplicationComponent

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        ContextProvider().create(this)
        KoinInitializer.initializeKoin()

        FirebaseApp.initializeApp(this)
        ApplicationComponent.init()
    }
}
