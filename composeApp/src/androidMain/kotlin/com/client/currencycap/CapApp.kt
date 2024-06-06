package com.client.currencycap

import KoinInitializer
import android.app.Application

class CapApp : Application() {

    override fun onCreate() {
        super.onCreate()

        KoinInitializer(applicationContext).init()
    }
}