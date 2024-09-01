package data.remote.model.requests

import data.util.APIConst.NEWS_URL
import io.ktor.resources.Resource

@Resource(NEWS_URL)
class GetNews