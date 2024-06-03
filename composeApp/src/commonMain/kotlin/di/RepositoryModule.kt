package di

import data.repository.MainRepository
import data.repository.MainRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MainRepository> { MainRepositoryImpl(get()) }
}