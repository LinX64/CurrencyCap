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

actual fun String.getCountryName(): String {
    return Locale("", this).displayCountry
}

actual fun String.getCountryFlag(): String {
    val countryCode = this.uppercase(Locale.ROOT)
    val firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6
    val secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6
    return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
}