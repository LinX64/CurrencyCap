package di

import data.local.database.ArticleLocalDataSourceImpl
import data.local.database.MarketChartDataLocalRepositoryImpl
import data.local.database.RatesLocalDataSourceImpl
import data.local.datastore.app.AppPreferences
import data.local.datastore.app.AppPreferencesImpl
import data.local.datastore.user.CurrencyRepositoryImpl
import data.local.datastore.user.UserPreferencesImpl
import data.remote.repository.assets.AssetsRepositoryImpl
import data.remote.repository.auth.AuthServiceRepositoryImpl
import data.remote.repository.explore.ExploreRepositoryImpl
import data.remote.repository.main.CryptoRepositoryImpl
import data.remote.repository.main.MainRepositoryImpl
import data.remote.repository.news.NewsRepositoryImpl
import data.remote.repository.profile.ProfileRepositoryImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import domain.repository.ArticleLocalDataSource
import domain.repository.AssetsRepository
import domain.repository.AuthServiceRepository
import domain.repository.CryptoRepository
import domain.repository.CurrencyRepository
import domain.repository.ExploreRepository
import domain.repository.MainRepository
import domain.repository.MarketChartDataLocalRepository
import domain.repository.NewsRepository
import domain.repository.ProfileRepository
import domain.repository.RatesLocalDataSource
import domain.repository.UserPreferences
import org.koin.dsl.module

val repositoryModule = module {
    // Temporary solution for the crash with Koin
    single { Firebase.auth }
    single { Firebase.firestore }

    single<MainRepository> { MainRepositoryImpl(get(), get()) }
    single<AuthServiceRepository> { AuthServiceRepositoryImpl(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
    single<UserPreferences> { UserPreferencesImpl(get()) }
    single<AppPreferences> { AppPreferencesImpl(get()) }
    single<CurrencyRepository> { CurrencyRepositoryImpl(get()) }
    single<NewsRepository> { NewsRepositoryImpl(get(), get()) }
    single<CryptoRepository> { CryptoRepositoryImpl(get(), get()) }
    single<ExploreRepository> { ExploreRepositoryImpl(get()) }
    single<AssetsRepository> { AssetsRepositoryImpl(get(), get()) }

    // Local repositories
    single<ArticleLocalDataSource> { ArticleLocalDataSourceImpl(get()) }
    single<RatesLocalDataSource> { RatesLocalDataSourceImpl(get()) }
    single<MarketChartDataLocalRepository> { MarketChartDataLocalRepositoryImpl(get()) }
}