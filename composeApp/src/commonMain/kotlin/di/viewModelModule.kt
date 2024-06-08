package di

import org.koin.dsl.module
import ui.screens.main.MainViewModel

val viewModelModule = module {
    single { MainViewModel(get(), get()) }
}