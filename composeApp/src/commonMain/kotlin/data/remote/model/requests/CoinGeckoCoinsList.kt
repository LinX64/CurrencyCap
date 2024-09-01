package data.remote.model.requests

import data.util.APIConst.BASE_COIN_GECKO_URL
import data.util.APIConst.CRYPTO_INFO_URL
import io.ktor.resources.Resource

@Resource("$BASE_COIN_GECKO_URL$CRYPTO_INFO_URL")
class CoinGeckoCoinsList
