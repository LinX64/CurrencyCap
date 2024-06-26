package ui.common

expect fun formatCurrentTotal(currentTotal: Long): String
expect fun formatToPrice(price: Double): String
expect fun String.getCountryName(): String
expect fun String.getCountryFlag(): String
expect fun getSettingsPreferencesPath(): String
expect class DecimalFormat() {
    fun format(double: Double): String
}

fun String.formatDecimalSeparator(): String {
    val split = this.split(".")
    println("string $this $split")
    var result = ""
    if (split.isEmpty() || split.size < 2) {
        result = this.reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()
    } else {
        result = split.first().reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()
        result = result + "." + split.last()
    }
    return result
}