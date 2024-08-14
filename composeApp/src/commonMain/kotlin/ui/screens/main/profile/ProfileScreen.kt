package ui.screens.main.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.contact_email
import currencycap.composeapp.generated.resources.support_request
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import org.jetbrains.compose.resources.stringResource
import ui.common.SendMail
import ui.components.base.BaseGlassLazyColumn
import ui.components.base.HandleNavigationEffect
import ui.screens.main.profile.ProfileNavigationEffect.NavigateToLanding
import ui.screens.main.profile.ProfileNavigationEffect.OpenEmailApp
import ui.screens.main.profile.ProfileNavigationEffect.ShowError
import ui.screens.main.profile.ProfileState.Loading
import ui.screens.main.profile.ProfileState.Success
import ui.screens.main.profile.ProfileViewEvent.OnDeleteAccountCardClicked
import ui.screens.main.profile.ProfileViewEvent.OnSignOutClicked
import ui.screens.main.profile.components.AppNameInfoCard
import ui.screens.main.profile.components.DeleteAccountCard
import ui.screens.main.profile.components.HelpCenterCard
import ui.screens.main.profile.components.ProfileCard
import ui.theme.AppDimensions.SPACER_PADDING_16

@Composable
internal fun ProfileRoute(
    hazeState: HazeState,
    profileViewModel: ProfileViewModel = koinViewModel<ProfileViewModel>(),
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit,
) {
    val state by profileViewModel.viewState.collectAsStateWithLifecycle()
    var shouldGoToEmailApp by remember { mutableStateOf(false) }

    ProfileScreen(
        hazeState = hazeState,
        state = state,
        shouldGoToEmailApp = shouldGoToEmailApp,
        handleEvent = profileViewModel::handleEvent,
    )

    HandleNavigationEffect(profileViewModel) { effect ->
        when (effect) {
            is NavigateToLanding -> onNavigateToLanding()
            is ShowError -> onError(effect.message)
            OpenEmailApp -> shouldGoToEmailApp = true
        }
    }
}

@Composable
internal fun ProfileScreen(
    hazeState: HazeState,
    state: ProfileState,
    shouldGoToEmailApp: Boolean,
    handleEvent: (ProfileViewEvent) -> Unit,
) {
    Box {
        BaseGlassLazyColumn(
            hazeState = hazeState,
            verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_16)
        ) {
            item {
                if (state is Success) {
                    val profileState = state.user
                    ProfileCard(user = profileState, isLoading = false)
                }
            }
            item {
                HelpCenterCard(onButtonClick = { handleEvent(ProfileViewEvent.OnSupportClicked) })
            }
            item {
                DeleteAccountCard(onDeleteAccountClicked = { handleEvent(OnDeleteAccountCardClicked) })
            }
            item {
                AppNameInfoCard(
                    isLoading = state is Loading,
                    onSignOutClicked = { handleEvent(OnSignOutClicked) }
                )
            }
        }
    }

    if (shouldGoToEmailApp) {
        SendMail(
            to = stringResource(Res.string.contact_email),
            subject = stringResource(Res.string.support_request)
        )
    }
}