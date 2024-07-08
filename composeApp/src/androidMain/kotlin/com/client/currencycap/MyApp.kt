package com.client.currencycap

import android.app.Application
import com.google.firebase.FirebaseApp
import ui.common.KoinInitializer
import util.ApplicationComponent

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        KoinInitializer.initializeKoin()

        FirebaseApp.initializeApp(this)
        ApplicationComponent.init()
    }
}
