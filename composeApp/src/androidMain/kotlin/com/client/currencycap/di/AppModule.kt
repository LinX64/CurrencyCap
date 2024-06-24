package com.client.currencycap.di

import org.koin.dsl.module
import ui.screens.MainViewModel

val appModule = module {
    single { MainViewModel(get()) }
}