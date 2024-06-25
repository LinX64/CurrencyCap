package domain.usecase

import domain.repository.MainRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ConvertRateUseCase(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(
        from: String,
        to: String,
        amount: String
    ): String = coroutineScope {
        val rates = mainRepository.getAllRates().map { it.rates }.first()

        val usdPrice = rates.find { it.symbol == from }?.rateUsd?.toDouble() ?: 0.0
        val finalAmount = usdPrice.times(amount.toDouble())
        val toUsdPrice = rates.find { it.symbol == to }?.rateUsd?.toDouble() ?: 0.0

        val result = finalAmount.div(toUsdPrice)
        return@coroutineScope result.toString()
    }
    // TODO: Use binary search to find the rate
}