package util

import data.util.APIConst
import domain.model.main.Rate
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

fun List<Rate>.getTextFieldOptions(): List<String> = this
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

fun encodeUrl(url: String): String {
    return url.replace("/", "%2F")
        .replace(":", "%3A")
        .replace("?", "%3F")
        .replace("&", "%26")
        .replace("=", "%3D")
        .replace("#", "%23")
        .replace(" ", "%20")
}

fun convertDateFormat(inputDate: String): String {
    val (year, month, day) = inputDate.split("T")[0].split("-")
    val monthName = when (month) {
        "01" -> "Jan"
        "02" -> "Feb"
        "03" -> "Mar"
        "04" -> "Apr"
        "05" -> "May"
        "06" -> "Jun"
        "07" -> "Jul"
        "08" -> "Aug"
        "09" -> "Sep"
        "10" -> "Oct"
        "11" -> "Nov"
        "12" -> "Dec"
        else -> throw IllegalArgumentException("Invalid month: $month")
    }

    val formattedDay = day.toInt().toString()
    return "$formattedDay $monthName, $year"
}