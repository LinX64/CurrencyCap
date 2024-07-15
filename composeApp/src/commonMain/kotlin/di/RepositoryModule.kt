package di

import data.local.database.ArticleLocalDataSourceImpl
import data.local.datastore.app.AppPreferences
import data.local.datastore.app.AppPreferencesImpl
import data.local.datastore.user.CurrencyRepositoryImpl
import data.local.datastore.user.UserPreferencesImpl
import data.remote.repository.auth.AuthServiceRepositoryImpl
import data.remote.repository.exchange.ExchangeRepositoryImpl
import data.remote.repository.main.MainRepositoryImpl
import data.remote.repository.news.NewsRepositoryImpl
import data.remote.repository.profile.ProfileRepositoryImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import domain.repository.ArticleLocalDataSource
import domain.repository.AuthServiceRepository
import domain.repository.CurrencyRepository
import domain.repository.ExchangeRepository
import domain.repository.MainRepository
import domain.repository.NewsRepository
import domain.repository.ProfileRepository
import domain.repository.UserPreferences
import org.koin.dsl.module

val repositoryModule = module {
    // Temporary solution for the crash with Koin
    single { Firebase.auth }
    single { Firebase.firestore }

    single<MainRepository> { MainRepositoryImpl(get()) }
    single<AuthServiceRepository> { AuthServiceRepositoryImpl(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
    single<UserPreferences> { UserPreferencesImpl(get()) }
    single<AppPreferences> { AppPreferencesImpl(get()) }
    single<CurrencyRepository> { CurrencyRepositoryImpl(get()) }
    single<ExchangeRepository> { ExchangeRepositoryImpl(get()) }
    single<NewsRepository> { NewsRepositoryImpl(get(), get()) }

    // Local repositories
    single<ArticleLocalDataSource> { ArticleLocalDataSourceImpl(get()) }
}