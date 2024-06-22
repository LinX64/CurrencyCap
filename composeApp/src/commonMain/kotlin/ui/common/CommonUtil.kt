package ui.common

expect fun formatCurrentTotal(currentTotal: Long): String
expect fun formatToPrice(price: Double): String
expect fun String.getCountryName(): String
expect fun String.getCountryFlag(): String
expect fun getSettingsPreferencesPath(): String
