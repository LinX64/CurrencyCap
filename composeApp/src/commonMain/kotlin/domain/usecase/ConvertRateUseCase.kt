package domain.usecase

import domain.repository.MainRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class ConvertRateUseCase(
    private val mainRepository: MainRepository
) {

    private lateinit var rateMap: Map<String, Double>

    suspend operator fun invoke(
        from: String,
        to: String,
        amount: String
    ): String = coroutineScope {
        if (!::rateMap.isInitialized) {
            val rates = mainRepository.getAllRates().map { it.rates }.firstOrNull()
                ?: return@coroutineScope "0.0"
            rateMap = rates.associateBy({ it.symbol.take(2).uppercase() }, { it.rateUsd.toDoubleOrNull() ?: 0.0 })
        }

        val fromRate = rateMap[from.uppercase()]
        val toRate = rateMap[to.uppercase()]
        val amountValue = amount.toDoubleOrNull()

        if (fromRate == null || toRate == null || amountValue == null || amountValue == 0.0) {
            return@coroutineScope "0.0"
        }

        val usdAmount = fromRate * amountValue
        val convertedAmount = usdAmount / toRate
        return@coroutineScope convertedAmount.toString()
    }
}
