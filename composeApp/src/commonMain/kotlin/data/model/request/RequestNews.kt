package data.model.request

import io.ktor.resources.Resource


/**
 * Type-safe request
 * https://ktor.io/docs/client-resources.html#resource_url
 */

@Resource("/news.json")
class RequestNews

// TODO; use type-safe requests for all API calls