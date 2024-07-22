package di

import domain.usecase.ConvertCurrenciesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { ConvertCurrenciesUseCase() }
}