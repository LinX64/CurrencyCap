package ui.screens.main.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.GlassCard
import ui.screens.main.settings.components.SettingsGeneralItem
import ui.screens.main.settings.components.SettingsHeaderText
import ui.screens.main.settings.components.SettingsMoreItem

@Composable
internal fun SettingsScreen(
    padding: PaddingValues,
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit,
    hazeState: HazeState,
    settingsViewModel: SettingsViewModel = koinViewModel<SettingsViewModel>()
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SettingsContent(
            onContactUsClick = { /* Handle click */ }
        )
    }
//
//    HandleNavigationEffect(settingsViewModel) { effect ->
//        when (effect) {
//            is NavigateToLanding -> onNavigateToLanding()
//        }
//    }

//    when (state) {
//        is SettingsState.Error -> onError((state as SettingsState.Error).message)
//        else -> Unit
//    }
    // TODO: finish the implementation
}

@Composable
private fun SettingsContent(
    onContactUsClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                SettingsHeaderText("Account Settings")
                Column(modifier = Modifier.fillMaxWidth()) {
                    SettingsMoreItem(text = "Edit Profile", onClick = { /* Handle switch change */ })
                    SettingsGeneralItem(text = "Dark Mode", onSwitchChange = { /* Handle switch change */ })
                    SettingsGeneralItem(text = "Push Notifications", onSwitchChange = { /* Handle switch change */ })
                }
            }
        }

        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                SettingsHeaderText("Policies")
                Column(modifier = Modifier.fillMaxWidth()) {
                    SettingsMoreItem(text = "About Us", onClick = { /* Handle click */ })
                    SettingsMoreItem(text = "Privacy Policy", onClick = { /* Handle click */ })
                    SettingsMoreItem(text = "Terms of conditions", onClick = { /* Handle click */ })
                }
            }
        }

        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                SettingsHeaderText("Support")
                Column(modifier = Modifier.fillMaxWidth()) {
                    SettingsMoreItem(text = "Contact Us", onClick = onContactUsClick)
                    // SettingsMoreItem(text = "FAQs", onClick = { /* Handle click */ })
                }
            }
        }
    }
}

