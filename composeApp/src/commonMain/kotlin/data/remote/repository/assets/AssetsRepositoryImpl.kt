package data.remote.repository.assets

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.remote.model.AssetPriceItemDto
import data.remote.model.convertJsonToAssetPrices
import data.remote.model.toEntity
import data.util.APIConst.LIVE_PRICES
import domain.model.AssetPriceItem
import domain.repository.AssetsRepository
import domain.repository.RatesLocalDataSource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder

class AssetsRepositoryImpl(
    private val httpClient: HttpClient,
    private val ratesLocalDataSource: RatesLocalDataSource,
) : AssetsRepository {
    private var pricesState by mutableStateOf<Map<String, AssetPriceItemDto>>(emptyMap())

    override fun getLiveAssetRates(): Store<String, List<AssetPriceItem>> {
        return StoreBuilder.from(
            fetcher = Fetcher.ofFlow { fetchLivePrices() },
            sourceOfTruth = SourceOfTruth.of(
                reader = { ratesLocalDataSource.getLivePricesFromDb() },
                writer = { _: String, liveRates ->
                    ratesLocalDataSource.insertLivePrices(liveRates.toEntity())
                },
                deleteAll = { ratesLocalDataSource.deleteAllLivePrices() },
            )
        ).build()
    }

    private fun fetchLivePrices(): Flow<List<AssetPriceItemDto>> = flow {
        httpClient.webSocket(urlString = LIVE_PRICES) {
            val incomingChannel = Channel<String>()

            val receiveJob = launch {
                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        incomingChannel.send(frame.readText())
                    }
                }
            }

            try {
                while (true) {
                    val newPrices = convertJsonToAssetPrices(incomingChannel.receive())
                    updatePrices(newPrices)
                    emit(pricesState.values.toList())
                }
            } finally {
                receiveJob.cancel()
                incomingChannel.close()
            }
        }
    }

    private fun updatePrices(newPrices: List<AssetPriceItemDto>) {
        pricesState = pricesState.toMutableMap().apply {
            newPrices.forEach { newPrice ->
                if (this[newPrice.symbol] != newPrice) {
                    this[newPrice.symbol] = newPrice
                }
            }
        }
    }
}