package com.client.currencycap

import android.app.Application
import di.KoinInitializer

class CapApp : Application() {

    override fun onCreate() {
        super.onCreate()

        KoinInitializer(applicationContext).init()
    }
}