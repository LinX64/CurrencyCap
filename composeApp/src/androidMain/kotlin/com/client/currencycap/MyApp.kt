package com.client.currencycap

import android.app.Application
import androidx.startup.AppInitializer
import com.google.firebase.FirebaseApp
import io.realm.kotlin.internal.RealmInitializer
import ui.common.ContextProvider
import ui.common.KoinInitializer
import util.ApplicationComponent

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        ContextProvider().create(this)

        val appInitializer = AppInitializer.getInstance(this)
        appInitializer.initializeComponent(RealmInitializer::class.java)

        KoinInitializer.initializeKoin()

        FirebaseApp.initializeApp(this)
        ApplicationComponent.init()
    }
}
