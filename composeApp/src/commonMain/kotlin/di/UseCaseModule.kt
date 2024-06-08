package di

import domain.usecase.GetRatesUseCase
import domain.usecase.GetTopMoversUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetRatesUseCase(get()) }
    single { GetTopMoversUseCase(get()) }
}