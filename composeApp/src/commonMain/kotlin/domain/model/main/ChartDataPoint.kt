package domain.model.main

import androidx.compose.runtime.Stable

@Stable
data class ChartDataPoint(
    val price: String,
    val timestamp: Long,
)
