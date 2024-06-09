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

    val formattedPrice = String.format(Locale.US, "%,.2f", price).trimEnd('0').trimEnd('.')
    return formattedPrice
}