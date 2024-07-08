package ui.common

import di.dataModule
import di.dataStoreModule
import di.httpClientModule
import di.repositoryModule
import di.useCaseModule
import di.viewModelModule
import org.koin.core.context.startKoin
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object KoinInitializer {
    fun initializeKoin() = startKoin {
        modules(
            listOf(
                httpClientModule,
                repositoryModule,
                viewModelModule,
                useCaseModule,
                dataStoreModule,
                viewModelModule,
                dataModule
            )
        )
    }
}