package data.remote.repository.exchange

import data.util.NetworkResult.Error
import data.util.NetworkResult.Success
import domain.model.Currency
import domain.model.main.Rate
import domain.repository.ExchangeRepository
import domain.repository.MainRepository
import kotlinx.collections.immutable.persistentSetOf
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
        val ratesFlow = mainRepository.getAllRates(forceRefresh)
            .mapNotNull { (it as? Success)?.data ?: (it as? Error)?.data }
            .map { it.rates }
            .map { it.filter { rate -> rate.type == FIAT && isRecognizedSymbol(rate) } }

        val mapToCurrency = ratesFlow.map { rates ->
            rates.map { rate ->
                Currency(code = rate.symbol, value = rate.rateUsd.toDouble())
            }
        }.first()
        emit(mapToCurrency)
    }

    private val unrecognizedSymbols = persistentSetOf(
        "CNH", "XAG", "PEN", "UYU", "AWG", "KYD", "XOF", "XPT",
        "XPF", "XAU", "XDR", "BND", "UGX", "XCD", "BIF", "XPD",
        "SSP", "SZL", "SHP"
    )

    private fun isRecognizedSymbol(it: Rate) = it.symbol !in unrecognizedSymbols

    private companion object {
        const val FIAT = "fiat"
    }
}

