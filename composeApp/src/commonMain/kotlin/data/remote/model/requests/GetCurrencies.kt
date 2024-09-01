package data.remote.model.requests

import data.util.APIConst.CURRENCIES_URL
import io.ktor.resources.Resource

@Resource(CURRENCIES_URL)
class GetCurrencies
