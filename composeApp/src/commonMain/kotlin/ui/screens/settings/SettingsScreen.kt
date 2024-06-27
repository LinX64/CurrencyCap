package ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.BlurColumn
import ui.screens.settings.components.DarkModeItem
import ui.screens.settings.components.PushNotificationItem
import ui.screens.settings.components.SettingsMoreItem

@Composable
internal fun SettingsScreen(
    padding: PaddingValues,
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit,
    hazeState: HazeState,
    settingsViewModel: SettingsViewModel = koinViewModel<SettingsViewModel>(),
) {
    val state by settingsViewModel.viewState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(PaddingValues(16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SettingsGeneralCard()
    }
//
//    HandleNavigationEffect(settingsViewModel) { effect ->
//        when (effect) {
//            is NavigateToLanding -> onNavigateToLanding()
//        }
//    }

    when (state) {
        is SettingsState.Error -> onError((state as SettingsState.Error).message)
        else -> Unit
    }
}

@Composable
private fun SettingsGeneralCard() {
    BlurColumn {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            SettingsHeaderText("Account Settings")

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                DarkModeItem(text = "Dark Mode")

                Spacer(modifier = Modifier.height(8.dp))

                PushNotificationItem(text = "Push Notifications")
            }

            Spacer(modifier = Modifier.height(24.dp))

            SettingsHeaderText("More")

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                SettingsMoreItem(text = "About Us", onClick = { /* Handle click */ })

                Spacer(modifier = Modifier.height(8.dp))

                SettingsMoreItem(text = "Privacy Policy", onClick = { /* Handle click */ })
            }
        }
    }
}

@Composable
private fun SettingsHeaderText(
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(20.dp)
                .background(Color(0xFF1E88E5))
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}