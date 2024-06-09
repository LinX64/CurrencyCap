package di

import data.repository.auth.AuthService
import data.repository.auth.AuthServiceImpl
import data.repository.main.MainRepository
import data.repository.main.MainRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MainRepository> { MainRepositoryImpl(get()) }
    single<AuthService> { AuthServiceImpl(get()) }
}