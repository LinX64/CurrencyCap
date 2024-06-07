package di

import data.repository.MainRepository
import data.repository.MainRepositoryImpl
import org.koin.dsl.module

actual val repositoryModule = module {
    single<MainRepository> { MainRepositoryImpl(get()) }
}