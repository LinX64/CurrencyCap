package util

import data.util.APIConst
import domain.model.Currency
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

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

internal fun normalizeRateUsd(currency: Currency): Double = if (currency.code == "USD") {
    currency.value
} else 1.0 / currency.value

fun formatNumber(number: Number): String {
    val num = number.toDouble()
    return when {
        num >= 1_000_000_000 -> {
            val billions = num / 1_000_000_000
            "${(billions * 10).toInt() / 10.0}B"
        }

        num >= 1_000_000 -> {
            val millions = num / 1_000_000
            "${(millions * 10).toInt() / 10.0}M"
        }

        num >= 1_000 -> {
            val thousands = num / 1_000
            "${(thousands * 10).toInt() / 10.0}K"
        }

        else -> {
            "${(num * 100).toInt() / 100.0}"
        }
    }
}

fun convertToLocalDate(dateString: String): LocalDate {
    return when {
        dateString.contains('T') -> Instant.parse(dateString).toLocalDateTime(TimeZone.UTC).date
        dateString.contains('.') -> {
            val (day, month, year) = dateString.split('.').map { it.toInt() }
            LocalDate(year, month, day)
        }

        else -> throw IllegalArgumentException("Unsupported date format")
    }
}

