package ui.screens.subscribers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.screens.subscribers.components.FreePlanCard
import ui.screens.subscribers.components.ProPlanCard
import ui.screens.subscribers.components.SubscribersOnly

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun SubscribersSection(
    modifier: Modifier = Modifier
) {
    var bytes by remember { mutableStateOf(ByteArray(0)) }
    val composition by rememberLottieComposition(LottieCompositionSpec.JsonString(bytes.decodeToString()))
    val progress by animateLottieCompositionAsState(composition)

    LaunchedEffect(Unit) {
        bytes = Res.readBytes("files/premium.json")
    }
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        item {
            SubscribersOnly(
                modifier = modifier,
                composition = composition,
                progress = progress
            )
        }

        item {
            Text(
                modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                text = "Select Your Plan",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        items(2) {
            when (it) {
                0 -> FreePlanCard()
                1 -> ProPlanCard()
            }
        }

        item { SubscribeButton() }
    }
}

@Composable
private fun SubscribeButton(modifier: Modifier = Modifier) {
    Button(
        modifier = modifier.fillMaxWidth()
            .padding(16.dp)
            .height(52.dp),
        onClick = { /* TODO */ },
        shape = RoundedCornerShape(10.dp),
        enabled = true // TODO: Implement this
    ) {
        Row(
            modifier = modifier.padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Subscribe",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.surface
            )

            Spacer(modifier = modifier.width(16.dp))

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surface
            )
        }
    }
}
