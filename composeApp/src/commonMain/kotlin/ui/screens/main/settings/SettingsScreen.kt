package ui.screens.main.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import ui.components.base.GlassCard
import ui.screens.main.profile.components.HelpCenterItem
import ui.screens.main.settings.components.SettingsGeneralItem
import ui.screens.main.settings.components.SettingsHeaderText
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun SettingsScreen(
    padding: PaddingValues,
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit,
    hazeState: HazeState,
    //settingsViewModel: SettingsViewModel = koinViewModel<SettingsViewModel>()
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
internal fun SettingsContent(
    onContactUsClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(SPACER_PADDING_16),
        verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_8),
    ) {
        GlassCard {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                SettingsHeaderText("General")
                Column(modifier = Modifier.fillMaxWidth()) {
                    SettingsGeneralItem(text = "Push Notifications", onSwitchChange = { /* Handle switch change */ })
                    //HelpCenterItem(text = "Edit Profile", onButtonClick = onContactUsClick)
                    // SettingsGeneralItem(text = "Dark Mode", onSwitchChange = { /* Handle switch change */ })

                    //HelpCenterItem(text = "Change Password", onButtonClick = onContactUsClick)
                }
            }
        }

        GlassCard {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                SettingsHeaderText("Policies")

                Spacer(modifier = Modifier.height(SPACER_PADDING_8))

                Column(modifier = Modifier.fillMaxWidth()) {
                    HelpCenterItem(text = "About Us", onButtonClick = { /* Handle click */ })
                    HelpCenterItem(text = "Privacy Policy", onButtonClick = { /* Handle click */ })
                    // HelpCenterItem(text = "Terms of conditions", onButtonClick = { /* Handle click */ })
                }
            }
        }
    }
}

