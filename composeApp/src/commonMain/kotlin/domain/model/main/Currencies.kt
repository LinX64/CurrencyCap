package domain.model.main

import androidx.compose.runtime.Immutable

@Immutable
data class Currencies(
    val timestamp: Long,
    val bonbast: List<BonbastRate>,
    val crypto: List<Crypto>,
    val markets: List<Market>,
    val rates: List<Rate>
)