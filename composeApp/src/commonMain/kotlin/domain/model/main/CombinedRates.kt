package domain.model.main

import androidx.compose.runtime.Stable

@Stable
data class CombinedRates(
    val bonbastRates: List<BonbastRate>,
    val cryptoRates: List<Crypto>,
    val markets: List<Market>,
    val fiatRates: List<Rate>,
    val topMovers: List<Crypto>,
)