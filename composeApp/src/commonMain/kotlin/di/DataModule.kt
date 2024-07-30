package di

import data.local.model.ArticleEntity
import data.local.model.main.BonbastRateEntity
import data.local.model.main.CryptoEntity
import data.local.model.main.CurrenciesEntity
import data.local.model.main.MarketEntity
import data.local.model.main.RateEntity
import io.realm.kotlin.Configuration
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val dataModule = module {
    single<Configuration> {
        RealmConfiguration.Builder(
            schema = setOf(
                ArticleEntity::class,
                CurrenciesEntity::class,
                BonbastRateEntity::class,
                CryptoEntity::class,
                MarketEntity::class,
                RateEntity::class
            )
        )
            .compactOnLaunch()
            .build()
    }
    single<Realm> {
        Realm.open(get())
    }
}