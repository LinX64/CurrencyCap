package di

import data.repository.auth.AuthServiceImpl
import data.repository.datastore.app.AppPreferences
import data.repository.datastore.app.AppPreferencesImpl
import data.repository.datastore.user.UserPreferences
import data.repository.datastore.user.UserPreferencesImpl
import data.repository.main.MainRepositoryImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import domain.repository.AuthService
import domain.repository.MainRepository
import org.koin.dsl.module

val repositoryModule = module {
    // Temporary solution for the crash with Koin
    single { Firebase.auth }

    single<MainRepository> { MainRepositoryImpl(get()) }
    single<AuthService> { AuthServiceImpl(get()) }
    single<UserPreferences> { UserPreferencesImpl(get()) }
    single<AppPreferences> { AppPreferencesImpl(get()) }
}