package ui.screens.exchange.components

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CurrencyVisualTransformation(private val countryCode: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val code = countryCode.uppercase().take(2)
        val symbol = getCurrencySymbolForCountry(code)
        val transformedText = symbol + text.text

        return TransformedText(
            AnnotatedString(transformedText),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return offset + symbol.length
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return if (offset < symbol.length) 0 else offset - symbol.length
                }
            }
        )
    }

    private fun getCurrencySymbolForCountry(countryCode: String): String {
        return when (countryCode) {
            "US" -> "$"        // United States Dollar
            "AE" -> "د.إ"      // United Arab Emirates Dirham
            "AF" -> "؋"        // Afghan Afghani
            "AL" -> "L"        // Albanian Lek
            "AM" -> "֏"       // Armenian Dram
            "AO" -> "Kz"       // Angolan Kwanza
            "AR" -> "$"        // Argentine Peso
            "AU" -> "$"        // Australian Dollar
            "AZ" -> "₼"       // Azerbaijani Manat
            "BA" -> "KM"       // Bosnia and Herzegovina Convertible Mark
            "BD" -> "৳"       // Bangladeshi Taka
            "BG" -> "лв"       // Bulgarian Lev
            "BH" -> ".د.ب"     // Bahraini Dinar
            "BN" -> "$"        // Brunei Dollar
            "BO" -> "Bs."      // Bolivian Boliviano
            "BR" -> "R$"       // Brazilian Real
            "BT" -> "Nu."      // Bhutanese Ngultrum
            "BY" -> "Br"       // Belarusian Ruble
            "BZ" -> "$"        // Belize Dollar
            "CA" -> "$"        // Canadian Dollar
            "CH" -> "CHF"      // Swiss Franc
            "CL" -> "$"        // Chilean Peso
            "CN" -> "¥"        // Chinese Yuan
            "CO" -> "$"        // Colombian Peso
            "CR" -> "₡"       // Costa Rican Colón
            "CU" -> "$"        // Cuban Peso
            "CZ" -> "Kč"       // Czech Koruna
            "DK" -> "kr"       // Danish Krone
            "DO" -> "$"        // Dominican Peso
            "DZ" -> "دج"      // Algerian Dinar
            "EG" -> "£"        // Egyptian Pound
            "ET" -> "ብር"      // Ethiopian Birr
            "EU" -> "€"        // Euro
            "GB" -> "£"        // British Pound Sterling
            "GE" -> "₾"       // Georgian Lari
            "GH" -> "₵"       // Ghanaian Cedi
            "GT" -> "Q"        // Guatemalan Quetzal
            "HK" -> "$"        // Hong Kong Dollar
            "HN" -> "L"        // Honduran Lempira
            "HR" -> "kn"       // Croatian Kuna
            "HU" -> "Ft"       // Hungarian Forint
            "ID" -> "Rp"       // Indonesian Rupiah
            "IL" -> "₪"       // Israeli New Shekel
            "IN" -> "₹"       // Indian Rupee
            "IQ" -> "ع.د"      // Iraqi Dinar
            "IR" -> "﷼"      // Iranian Rial
            "IS" -> "kr"       // Icelandic Króna
            "JM" -> "$"        // Jamaican Dollar
            "JO" -> "د.ا"      // Jordanian Dinar
            "JP" -> "¥"        // Japanese Yen
            "KE" -> "KSh"      // Kenyan Shilling
            "KG" -> "сом"      // Kyrgyzstani Som
            "KH" -> "៛"       // Cambodian Riel
            "KP" -> "₩"       // North Korean Won
            "KR" -> "₩"       // South Korean Won
            "KW" -> "د.ك"      // Kuwaiti Dinar
            "KZ" -> "₸"       // Kazakhstani Tenge
            "LA" -> "₭"       // Lao Kip
            "LB" -> "ل.ل"      // Lebanese Pound
            "LK" -> "රු"      // Sri Lankan Rupee
            "LR" -> "$"        // Liberian Dollar
            "LS" -> "L"        // Lesotho Loti
            "LY" -> "ل.د"      // Libyan Dinar
            "MA" -> "د.م."     // Moroccan Dirham
            "MD" -> "L"        // Moldovan Leu
            "ME" -> "€"        // Euro
            "MG" -> "Ar"       // Malagasy Ariary
            "MK" -> "ден"      // Macedonian Denar
            "MM" -> "K"        // Myanmar Kyat
            "MN" -> "₮"       // Mongolian Tögrög
            "MO" -> "P"        // Macanese Pataca
            "MR" -> "UM"       // Mauritanian Ouguiya
            "MU" -> "₨"       // Mauritian Rupee
            "MV" -> "ރ"       // Maldivian Rufiyaa
            "MX" -> "$"        // Mexican Peso
            "MY" -> "RM"       // Malaysian Ringgit
            "MZ" -> "MT"       // Mozambican Metical
            "NA" -> "$"        // Namibian Dollar
            "NG" -> "₦"       // Nigerian Naira
            "NI" -> "C$"       // Nicaraguan Córdoba
            "NO" -> "kr"       // Norwegian Krone
            "NP" -> "₨"       // Nepalese Rupee
            "NZ" -> "$"        // New Zealand Dollar
            "OM" -> "ر.ع."     // Omani Rial
            "PA" -> "B/."      // Panamanian Balboa
            "PE" -> "S/"       // Peruvian Sol
            "PG" -> "K"        // Papua New Guinean Kina
            "PH" -> "₱"       // Philippine Peso
            "PK" -> "₨"       // Pakistani Rupee
            "PL" -> "zł"       // Polish Zloty
            "PY" -> "₲"       // Paraguayan Guarani
            "QA" -> "ر.ق"      // Qatari Riyal
            "RO" -> "lei"      // Romanian Leu
            "RS" -> "дин."     // Serbian Dinar
            "RU" -> "₽"       // Russian Ruble
            "RW" -> "FRw"      // Rwandan Franc
            "SA" -> "ر.س"      // Saudi Riyal
            "SD" -> "ج.س."     // Sudanese Pound
            "SE" -> "kr"       // Swedish Krona
            "SG" -> "$"        // Singapore Dollar
            "SI" -> "€"        // Euro
            "SK" -> "€"        // Euro
            "SL" -> "Le"       // Sierra Leonean Leone
            "SO" -> "Sh"       // Somali Shilling
            "SS" -> "£"        // South Sudanese Pound
            "SY" -> "£"        // Syrian Pound
            "SZ" -> "E"        // Eswatini Lilangeni
            "TH" -> "฿"       // Thai Baht
            "TJ" -> "ЅМ"       // Tajikistani Somoni
            "TM" -> "m"        // Turkmenistani Manat
            "TN" -> "د.ت"      // Tunisian Dinar
            "TR" -> "₺"       // Turkish Lira
            "TT" -> "$"        // Trinidad and Tobago Dollar
            "TW" -> "NT$"      // New Taiwan Dollar
            "TZ" -> "Sh"       // Tanzanian Shilling
            "UA" -> "₴"       // Ukrainian Hryvnia
            "UG" -> "USh"      // Ugandan Shilling
            "UY" -> "$"        // Uruguayan Peso
            "UZ" -> "сўм"      // Uzbekistani Soʻm
            "VE" -> "Bs."      // Venezuelan Bolívar
            "VN" -> "₫"       // Vietnamese đồng
            "YE" -> "﷼"      // Yemeni Rial
            "ZA" -> "R"        // South African Rand
            "ZM" -> "ZK"       // Zambian Kwacha
            "ZW" -> "$"        // Zimbabwean Dollar
            else -> "$"        // Default symbol
        }
    }
}