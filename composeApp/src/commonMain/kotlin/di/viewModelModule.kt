package di

import org.koin.dsl.module
import ui.screens.ai_predict.AiPredictViewModel
import ui.screens.exchange.ExchangeViewModel
import ui.screens.home.MainViewModel
import ui.screens.search.SearchViewModel

val viewModelModule = module {
    single { MainViewModel(get()) }
    single { ExchangeViewModel(get()) }
    single { AiPredictViewModel() }
    single { SearchViewModel() }
}