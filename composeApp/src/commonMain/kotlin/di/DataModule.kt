package di

import io.realm.kotlin.Configuration
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module


val dataModule = module {
    single<Configuration> {
        RealmConfiguration.Builder(
            schema = setOf(CurrencyEntity::class)
        ).compactOnLaunch().build()
    }
    single<Realm> {
        Realm.open(get())
    }
}