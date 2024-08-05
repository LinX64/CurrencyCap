package data.remote.repository.exchange

import data.util.NetworkResult.Error
import data.util.NetworkResult.Success
import domain.model.Currency
import domain.repository.ExchangeRepository
import domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class ExchangeRepositoryImpl(
    private val mainRepository: MainRepository
) : ExchangeRepository {

    override fun getLatest(
        forceRefresh: Boolean
    ): Flow<List<Currency>> = flow {
        val currencies = mainRepository.getAllRates(forceRefresh)
            .mapNotNull { result ->
                when (result) {
                    is Success -> result.data
                    is Error -> result.data
                    else -> null
                }
            }
            .map { data ->
                data.rates
                    .filter { it.type == FIAT && it.symbol !in unrecognizedSymbols }
                    .map { rate -> Currency(code = rate.symbol, value = rate.rateUsd.toDouble()) }
            }
            .first()

        emit(currencies)
    }

    companion object {
        private const val FIAT = "fiat"
        private val unrecognizedSymbols = setOf(
            "CNH", "XAG", "PEN", "UYU", "AWG", "KYD", "XOF", "XPT",
            "XPF", "XAU", "XDR", "BND", "UGX", "XCD", "BIF", "XPD",
            "SSP", "SZL", "SHP"
        )
    }
}

