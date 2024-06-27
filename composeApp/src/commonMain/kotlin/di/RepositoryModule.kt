package di

import CurrencyRepositoryImpl
import data.repository.auth.AuthServiceRepositoryImpl
import data.repository.datastore.app.AppPreferences
import data.repository.datastore.app.AppPreferencesImpl
import data.repository.datastore.user.UserPreferencesImpl
import data.repository.exchange.ExchangeRepositoryImpl
import data.repository.main.MainRepositoryImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import domain.repository.AuthServiceRepository
import domain.repository.CurrencyRepository
import domain.repository.ExchangeRepository
import domain.repository.MainRepository
import domain.repository.UserPreferences
import org.koin.dsl.module

val repositoryModule = module {
    // Temporary solution for the crash with Koin
    single { Firebase.auth }

    single<MainRepository> { MainRepositoryImpl(get()) }
    single<AuthServiceRepository> { AuthServiceRepositoryImpl(get()) }
    single<UserPreferences> { UserPreferencesImpl(get()) }
    single<AppPreferences> { AppPreferencesImpl(get()) }
    single<CurrencyRepository> { CurrencyRepositoryImpl(get()) }
    single<ExchangeRepository> { ExchangeRepositoryImpl(get()) }
}