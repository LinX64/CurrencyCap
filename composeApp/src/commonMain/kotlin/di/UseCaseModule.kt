package di

import domain.usecase.ConvertCurrenciesUseCase
import domain.usecase.ConvertRateUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { ConvertRateUseCase(get()) }
    single { ConvertCurrenciesUseCase() }
}