package ui.common

import java.text.DecimalFormat
import java.util.Locale

actual fun formatCurrentTotal(currentTotal: Long): String {
    val decimalFormat = DecimalFormat("$#,###")
    return decimalFormat.format(currentTotal)
}

actual fun formatToPrice(price: Double): String {
    if (price == price.toLong().toDouble()) {
        return String.format(Locale.US, "%,d", price.toLong())
    }

    val formattedPrice = String.format(Locale.US, "%,.10f", price).trimEnd('0').trimEnd('.')

    val decimalIndex = formattedPrice.indexOf('.')
    val finalPrice = if (decimalIndex != -1 && decimalIndex + 6 < formattedPrice.length) {
        formattedPrice.substring(0, decimalIndex + 6)
    } else {
        formattedPrice
    }

    return finalPrice
}