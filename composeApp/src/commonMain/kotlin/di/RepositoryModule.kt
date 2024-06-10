package di

import data.repository.auth.AuthServiceImpl
import data.repository.main.MainRepositoryImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import domain.repository.AuthService
import domain.repository.MainRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MainRepository> { MainRepositoryImpl(get()) }

    // Temporary solution for the crash of Koin
    single { Firebase.auth }
    single<AuthService> { AuthServiceImpl(get()) }
}