package ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.CenteredColumn
import ui.components.HandleNavigationEffect
import ui.components.main.BaseBlurLazyColumn
import ui.screens.profile.ProfileNavigationEffect.NavigateToLanding
import ui.screens.profile.ProfileViewEvent.OnDeleteAccountCardClicked
import ui.screens.profile.components.DeleteAccountCard
import ui.screens.profile.components.HelpCenterCard
import ui.screens.profile.components.ProfileCard

@Composable
internal fun ProfileScreen(
    padding: PaddingValues,
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit,
    hazeState: HazeState,
    profileViewModel: ProfileViewModel = koinViewModel<ProfileViewModel>(),
) {
    val state by profileViewModel.viewState.collectAsStateWithLifecycle()

    BaseBlurLazyColumn(
        padding = padding,
        hazeState = hazeState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            when (state) {
                is ProfileState.Success -> {
                    /// state as ProfileState.Success
                    ProfileCard()
                }

                ProfileState.Loading -> CenteredColumn {
                    CircularProgressIndicator()
                }

                else -> Unit
            }
        }
        item { HelpCenterCard() }
        item {
            DeleteAccountCard(
                onDeleteAccountClicked = { profileViewModel.handleEvent(OnDeleteAccountCardClicked(profileViewModel.uid.value)) }
            )
        }
    }

    HandleNavigationEffect(profileViewModel) { effect ->
        when (effect) {
            is NavigateToLanding -> onNavigateToLanding()
        }
    }

    when (state) {
        is ProfileState.Error -> onError((state as ProfileState.Error).message)
        else -> Unit
    }
}