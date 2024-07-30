package di

import domain.usecase.CombineRatesNewsUseCase
import domain.usecase.ConvertCurrenciesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { ConvertCurrenciesUseCase() }
    single { CombineRatesNewsUseCase(get(), get()) }
}