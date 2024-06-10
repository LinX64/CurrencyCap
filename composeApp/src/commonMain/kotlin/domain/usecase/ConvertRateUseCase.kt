package domain.usecase

import domain.model.DataDao
import domain.repository.MainRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first

class ConvertRateUseCase(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(
        from: String,
        to: String,
        amount: String
    ): String = coroutineScope {
        val rates = mainRepository.getCoinCapRates().first()
        val fromRate = findRateUsd(rates, from)
        val toRate = findRateUsd(rates, to)
        val amountValue = amount.toDoubleOrNull()

        if (fromRate == null || toRate == null || amountValue == null || amountValue == 0.0) {
            return@coroutineScope "0.0"
        }

        val usdAmount = fromRate * amountValue
        val convertedAmount = usdAmount / toRate

        return@coroutineScope convertedAmount.toString()
    }

    /**
     * Find the rate in USD for the given symbol using binary search
     * @param rates List of rates
     */
    private fun findRateUsd(rates: List<DataDao>, symbol: String): Double? {
        val index = rates.binarySearch { it.symbol.compareTo(symbol) }
        return if (index >= 0) rates[index].rateUsd.toDoubleOrNull() else null
    }
}