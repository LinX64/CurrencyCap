package util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateUtils {
    fun convertMillisToDate(millis: Long): String {
        val instant = Instant.fromEpochMilliseconds(millis)
        val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
        return "${localDate.dayOfMonth}.${localDate.monthNumber}.${localDate.year}"
    }
}