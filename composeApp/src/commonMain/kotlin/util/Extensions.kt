package util

import data.util.APIConst

internal fun Int.formatToPrice(): String = toString()
    .reversed()
    .chunked(3)
    .joinToString(",")
    .reversed()

internal fun getIconBy(symbol: String): String {
    val lowerCaseSymbol = symbol.lowercase()
    return APIConst.CRYPTO_ICON_API + lowerCaseSymbol + ".png"
}

fun String.validateEmail(): Boolean {
    val emailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
    return emailRegex.matches(this)
}
