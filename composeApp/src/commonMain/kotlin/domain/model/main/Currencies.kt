package domain.model.main

data class Currencies(
    val bonbast: List<BonbastRate>,
    val crypto: List<Crypto>,
    val markets: List<Market>,
    val rates: List<Rate>
)