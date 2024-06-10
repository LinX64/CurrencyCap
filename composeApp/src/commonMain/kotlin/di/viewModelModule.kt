package di

import org.koin.dsl.module
import ui.screens.ai_predict.AiPredictViewModel
import ui.screens.auth.login.LoginViewModel
import ui.screens.auth.register.RegisterViewModel
import ui.screens.exchange.ExchangeViewModel
import ui.screens.home.MainViewModel
import ui.screens.search.SearchViewModel

val viewModelModule = module {
    single { MainViewModel(get()) }
    single { ExchangeViewModel(get(), get()) }
    single { AiPredictViewModel() }
    single { SearchViewModel() }
    single { LoginViewModel(get()) }
    single { RegisterViewModel(get()) }
}

val previewModule = module {
    single { RegisterViewModel(get()) }
}