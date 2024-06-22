package com.client.currencycap.di

import com.client.currencycap.main.MainViewModel
import org.koin.dsl.module

val appModule = module {
    single { MainViewModel(get()) }
}