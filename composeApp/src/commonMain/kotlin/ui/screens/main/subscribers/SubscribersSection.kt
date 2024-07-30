package ui.screens.main.subscribers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.screens.main.subscribers.components.FreePlanCard
import ui.screens.main.subscribers.components.PremiumAccessOnly
import ui.screens.main.subscribers.components.ProPlanCard
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_32
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun SubscribersSection(
    modifier: Modifier = Modifier
) {

    var isPremium by rememberSaveable { mutableStateOf(false) }
    var selectedPlan by rememberSaveable { mutableStateOf<Plan?>(Plan.FREE) }

    LazyColumn(modifier = modifier.padding(SPACER_PADDING_16)) {
        item {
            PremiumAccessOnly()
        }

        item {
            Text(
                modifier = modifier.padding(horizontal = SPACER_PADDING_16, vertical = SPACER_PADDING_8),
                text = "Select Your Plan",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        items(2) {
            when (it) {
                0 -> FreePlanCard(
                    onFreePlanClick = {
                        selectedPlan = Plan.FREE
                        isPremium = false
                    },
                    isFreeCardSelected = selectedPlan == Plan.FREE
                )

                1 -> ProPlanCard(
                    onProPlanClick = {
                        selectedPlan = Plan.PRO
                        isPremium = true
                    },
                    isProCardSelected = selectedPlan == Plan.PRO
                )
            }
        }

        item { SubscribeButton(onSubscribeClick = {}, isPremium = isPremium) }
    }
}

private enum class Plan {
    FREE,
    PRO
}

@Composable
private fun SubscribeButton(
    modifier: Modifier = Modifier,
    onSubscribeClick: () -> Unit,
    isPremium: Boolean
) {
    Button(
        modifier = modifier.fillMaxWidth()
            .padding(SPACER_PADDING_16)
            .height(52.dp),
        onClick = onSubscribeClick,
        shape = RoundedCornerShape(20.dp),
        enabled = isPremium
    ) {
        Row(
            modifier = modifier.padding(horizontal = SPACER_PADDING_32),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Subscribe",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.surface
            )
        }
    }
}
