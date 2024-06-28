package data.remote.repository.exchange

import data.remote.model.exchange.Currency
import data.remote.model.main.Rate
import data.util.APIConst.BASE_URL
import data.util.parseCurrencyRates
import domain.repository.ExchangeRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ExchangeRepositoryImpl(
    private val httpClient: HttpClient
) : ExchangeRepository {

    override fun getLatest(): Flow<List<Currency>> = flow {
        val plainResponse = httpClient.get(BASE_URL).body<String>()
        val rates = parseCurrencyRates(plainResponse).rates
            .filter { it.type == FIAT && isRecognizedSymbol(it) }

        val mapToCurrency = rates.map { rate ->
            Currency(
                code = rate.symbol,
                value = rate.rateUsd.toDouble()
            )
        }

        emit(mapToCurrency)
    }.flowOn(Dispatchers.IO)

    private val unrecognizedSymbols = setOf(
        "CNH", "XAG", "PEN", "UYU", "AWG", "KYD", "XOF", "XPT",
        "XPF", "XAU", "XDR", "BND", "UGX", "XCD", "BIF", "XPD",
        "SSP", "SZL", "SHP"
    )

    private fun isRecognizedSymbol(it: Rate) = it.symbol !in unrecognizedSymbols

    private companion object {
        const val FIAT = "fiat"
    }
}

