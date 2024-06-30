package ui.screens.main.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.common.SendMail
import ui.components.HandleNavigationEffect
import ui.components.main.BaseGlassLazyColumn
import ui.screens.main.profile.ProfileNavigationEffect.NavigateToLanding
import ui.screens.main.profile.ProfileViewEvent.OnDeleteAccountCardClicked
import ui.screens.main.profile.components.DeleteAccountCard
import ui.screens.main.profile.components.HelpCenterCard
import ui.screens.main.profile.components.ProfileCard

@Composable
internal fun ProfileScreen(
    padding: PaddingValues,
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit,
    hazeState: HazeState,
    profileViewModel: ProfileViewModel = koinViewModel<ProfileViewModel>(),
) {
    val state by profileViewModel.viewState.collectAsStateWithLifecycle()
    val shouldGoToEmailApp = remember { mutableStateOf(false) }

    BaseGlassLazyColumn(
        padding = padding,
        hazeState = hazeState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        isEmpty = state is ProfileState.Loading,
        emptyContent = { CircularProgressIndicator() }
    ) {
        item {
            when (state) {
                is ProfileState.Success -> ProfileCard()
                else -> Unit
            }
        }
        item {
            HelpCenterCard(
                onButtonClick = { profileViewModel.handleEvent(ProfileViewEvent.OnSupportClicked) })
        }
        item {
            DeleteAccountCard(
                onDeleteAccountClicked = { profileViewModel.handleEvent(OnDeleteAccountCardClicked(profileViewModel.uid.value)) }
            )
        }
    }

    HandleNavigationEffect(profileViewModel) { effect ->
        when (effect) {
            is NavigateToLanding -> onNavigateToLanding()
            ProfileNavigationEffect.OpenEmailApp -> shouldGoToEmailApp.value = true
        }
    }

    when (state) {
        is ProfileState.Error -> onError((state as ProfileState.Error).message)
        else -> Unit
    }

    if (shouldGoToEmailApp.value) {
        SendMail("ash.wxrz@hotmail.com", "Support Request")
    }
}