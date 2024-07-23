package ui.screens.main.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import ui.components.base.BaseTransparentCircularProgressBar
import ui.components.base.HandleNavigationEffect
import ui.screens.main.profile.ProfileNavigationEffect.NavigateToLanding
import ui.screens.main.profile.ProfileNavigationEffect.OpenEmailApp
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
    padding: PaddingValues,
    onError: (message: String) -> Unit,
    hazeState: HazeState,
    onNavigateToLanding: () -> Unit,
    profileViewModel: ProfileViewModel = koinViewModel<ProfileViewModel>(),
) {
    val state by profileViewModel.viewState.collectAsStateWithLifecycle()
    var shouldGoToEmailApp by remember { mutableStateOf(false) }

    ProfileScreen(
        padding = padding,
        hazeState = hazeState,
        state = state,
        shouldGoToEmailApp = shouldGoToEmailApp,
        onError = onError,
        handleEvent = profileViewModel::handleEvent,
    )

    HandleNavigationEffect(profileViewModel) { effect ->
        when (effect) {
            is NavigateToLanding -> onNavigateToLanding()
            OpenEmailApp -> shouldGoToEmailApp = true
        }
    }
}

@Composable
internal fun ProfileScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    state: ProfileState,
    shouldGoToEmailApp: Boolean,
    onError: (message: String) -> Unit,
    handleEvent: (ProfileViewEvent) -> Unit,
) {
    Box {
        BaseGlassLazyColumn(
            padding = padding,
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
                AppNameInfoCard(onSignOutClicked = { handleEvent(OnSignOutClicked) })
            }
        }

        when (state) {
            is ProfileState.Error -> onError(state.message)
            is Loading -> BaseTransparentCircularProgressBar()
            else -> Unit
        }
    }

    if (shouldGoToEmailApp) {
        SendMail(
            to = stringResource(Res.string.contact_email),
            subject = stringResource(Res.string.support_request)
        )
    }
}