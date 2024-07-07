package domain.usecase

import data.local.model.exchange.Currency
import util.normalizeRateUsd

class ConvertCurrenciesUseCase {
    operator fun invoke(
        amount: Double?,
        sourceCurrency: Currency?,
        targetCurrency: Currency?
    ): Double {
        if (amount == null || sourceCurrency == null || targetCurrency == null) {
            return 1.0
        }

        val normalizedSourceRate = normalizeRateUsd(sourceCurrency)
        val normalizedTargetRate = normalizeRateUsd(targetCurrency)

        val exchangeRate = normalizedTargetRate / normalizedSourceRate
        return amount * exchangeRate
    }
}