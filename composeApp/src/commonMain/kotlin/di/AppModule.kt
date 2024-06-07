package di

import org.koin.dsl.module
import ui.screens.main.MainViewModel

val appModule = module {
    single { "Hello world!" }
}

val viewModelModule = module {
    single { MainViewModel(get()) }
}