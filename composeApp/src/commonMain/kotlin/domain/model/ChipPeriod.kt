package domain.model

enum class ChipPeriod(val displayName: String, val interval: String) {
    DAY("24H", "m1"),
    WEEK("1W", "h1"),
    MONTH("1M", "h6"),
    YEAR("1Y", "d1"),
    ALL("All", "d1")
}
