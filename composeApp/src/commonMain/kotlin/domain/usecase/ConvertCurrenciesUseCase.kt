package domain.usecase

import data.local.model.exchange.Currency

class ConvertCurrenciesUseCase {

    operator fun invoke(
        amount: Double,
        sourceCurrency: Currency?,
        targetCurrency: Currency?
    ): Double {
        if (sourceCurrency == null || targetCurrency == null) {
            return 0.0
        }

        val exchangeRate = targetCurrency.value / sourceCurrency.value
        val convertedValue = amount * exchangeRate

        return convertedValue
    }
}
