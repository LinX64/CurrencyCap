package domain.model

enum class ChipPeriod(val displayName: String, val days: String) {
    DAY("24H", "1"),
    WEEK("1W", "7"),
    MONTH("1M", "30"),
    YEAR("1Y", "365"),
    ALL("All", "max")
}