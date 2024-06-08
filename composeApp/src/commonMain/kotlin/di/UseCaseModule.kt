package di

import domain.usecase.GetRatesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetRatesUseCase(get()) }
}