package di

import data.repository.auth.AuthServiceImpl
import data.repository.main.MainRepositoryImpl
import domain.repository.AuthService
import domain.repository.MainRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MainRepository> { MainRepositoryImpl(get()) }
    single<AuthService> { AuthServiceImpl(get()) }
}