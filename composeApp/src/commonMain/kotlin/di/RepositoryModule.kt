package di

import CurrencyRepositoryImpl
import data.local.datastore.app.AppPreferences
import data.local.datastore.app.AppPreferencesImpl
import data.local.datastore.user.UserPreferencesImpl
import data.remote.repository.auth.AuthServiceRepositoryImpl
import data.remote.repository.exchange.ExchangeRepositoryImpl
import data.remote.repository.main.MainRepositoryImpl
import data.remote.repository.news.NewsRepositoryImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import domain.repository.AuthServiceRepository
import domain.repository.CurrencyRepository
import domain.repository.ExchangeRepository
import domain.repository.MainRepository
import domain.repository.NewsRepository
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
    single<NewsRepository> { NewsRepositoryImpl(get()) }
}