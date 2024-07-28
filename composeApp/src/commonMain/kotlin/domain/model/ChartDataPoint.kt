package domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class ChartDataPoint(val timestamp: Long, val price: Float)