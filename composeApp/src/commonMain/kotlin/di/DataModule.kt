package di

import data.local.model.ArticleEntity
import data.local.model.ChartDataPointEntity
import data.local.model.MarketChartDataEntity
import data.local.model.main.BonbastRateEntity
import data.local.model.main.CryptoEntity
import data.local.model.main.CurrenciesEntity
import data.local.model.main.MarketEntity
import data.local.model.main.RateEntity
import io.realm.kotlin.Configuration
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

const val REALM_SCHEMA_VERSION: Long = 3
const val REALM_FILE_NAME = "rates.realm"

val dataModule = module {
    single<Configuration> {
        RealmConfiguration.Builder(
            schema = setOf(
                ArticleEntity::class,
                CurrenciesEntity::class,
                BonbastRateEntity::class,
                CryptoEntity::class,
                MarketEntity::class,
                RateEntity::class,
                MarketChartDataEntity::class,
                ChartDataPointEntity::class
            )
        )
            .name(REALM_FILE_NAME)
            .schemaVersion(REALM_SCHEMA_VERSION)
            .compactOnLaunch()
            .build()
    }
    single<Realm> {
        Realm.open(get())
    }
}