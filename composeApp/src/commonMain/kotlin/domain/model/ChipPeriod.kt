package domain.model

enum class ChipPeriod(val displayName: String) {
    DAY("24H"),
    WEEK("1W"),
    MONTH("1M"),
    YEAR("1Y"),
    ALL("All")
}