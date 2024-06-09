package domain.usecase

import data.repository.main.MainRepository
import domain.model.DataDao
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetRatesUseCase(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(): Flow<List<DataDao>> = coroutineScope {
        val coinCapRatesDeferred = async { mainRepository.getCoinCapRates() }
        val iranianFiatDeferred = async { mainRepository.getIranianRate() }

        val coinCapRates = coinCapRatesDeferred.await()
        val iranianFiat = iranianFiatDeferred.await()

        combine(coinCapRates, iranianFiat) { rates, iranianRate ->
            rates.map { rate ->
                val sellPrice = iranianRate.find { it.code == USD_STRING }?.sell?.toDouble()
                val inUsdPrice = rate.rateUsd.toDouble()
                val price = sellPrice?.times(inUsdPrice).toString()
                rate.copy(rateUsd = price)
            }
        }
    }

    companion object {
        const val USD_STRING = "usd"
    }
}
