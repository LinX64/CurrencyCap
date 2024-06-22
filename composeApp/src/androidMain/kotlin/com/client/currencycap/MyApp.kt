package com.client.currencycap

import android.app.Application
import com.google.firebase.FirebaseApp
import util.ApplicationComponent

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        ApplicationComponent.init()
    }
}
