package domain.usecase

import domain.model.CurrencyRate
import util.normalizeRateUsd

class ConvertCurrenciesUseCase {
    operator fun invoke(
        amount: Double?,
        sourceCurrencyRate: CurrencyRate?,
        targetCurrencyRate: CurrencyRate?
    ): Double {
        if (amount == null || sourceCurrencyRate == null || targetCurrencyRate == null) {
            return 1.0
        }

        val (normalizedSourceRate, isSourceReversed) = normalizeRateUsd(sourceCurrencyRate)
        val (normalizedTargetRate, isTargetReversed) = normalizeRateUsd(targetCurrencyRate)

        val exchangeRate = if (isSourceReversed == isTargetReversed) {
            normalizedTargetRate / normalizedSourceRate
        } else if (isSourceReversed) {
            normalizedTargetRate * normalizedSourceRate
        } else {
            normalizedTargetRate / normalizedSourceRate
        }

        return amount * exchangeRate
    }
}