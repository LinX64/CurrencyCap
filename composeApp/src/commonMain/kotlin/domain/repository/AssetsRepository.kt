package domain.repository

import domain.model.AssetPriceItem
import org.mobilenativefoundation.store.store5.Store

interface AssetsRepository {
    fun getLiveAssetRates(): Store<String, List<AssetPriceItem>>
}