package util

import data.util.APIConst
import domain.model.RateDto
import ui.common.getCountryFlag
import ui.common.getCountryName

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

fun List<RateDto>.getTextFieldOptions(): List<String> = this
    .asSequence()
    .map { it.symbol }
    .sorted()
    .map { symbol ->
        val countryCode = symbol.take(2)
        val countryName = countryCode.getCountryName()
        val flag = countryCode.getCountryFlag()
        StringBuilder().apply {
            append(flag)
            append("  ")
            append(symbol)
            append(" - ")
            append(countryName)
        }.toString()
    }
    .toList()

fun String.formatDecimalSeparator(): String {
    val parts = this.split(".")
    val integerPart = parts[0]
    val formattedIntegerPart = integerPart.reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()

    return if (parts.size > 1) {
        val decimalPart = parts[1]
        "$formattedIntegerPart.$decimalPart"
    } else {
        formattedIntegerPart
    }
}
