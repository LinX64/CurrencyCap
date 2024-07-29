package domain.model

enum class ChipPeriod(val displayName: String, val days: String, val interval: String) {
    DAY("24H", "1", "m1"),
    WEEK("1W", "7", "h1"),
    MONTH("1M", "30", "h6"),
    YEAR("1Y", "365", "d1"),
    ALL("All", "max", "d1")
}
