package ui.screens.main.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.contact_email
import currencycap.composeapp.generated.resources.support_request
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.common.SendMail
import ui.components.base.HandleNavigationEffect
import ui.components.base.button.SecondaryButton
import ui.components.main.BaseGlassLazyColumn
import ui.screens.main.profile.ProfileNavigationEffect.NavigateToLanding
import ui.screens.main.profile.ProfileNavigationEffect.OpenEmailApp
import ui.screens.main.profile.ProfileState.Loading
import ui.screens.main.profile.ProfileState.Success
import ui.screens.main.profile.ProfileViewEvent.OnDeleteAccountCardClicked
import ui.screens.main.profile.ProfileViewEvent.OnSignOutClicked
import ui.screens.main.profile.components.DeleteAccountCard
import ui.screens.main.profile.components.HelpCenterCard
import ui.screens.main.profile.components.ProfileCard
import util.getDummyUser

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
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            when (state) {
                is Success -> {
                    val profileState = (state as Success).user
                    ProfileCard(user = profileState, isLoading = false)
                }

                is Loading -> ProfileCard(user = getDummyUser(), isLoading = true)
                else -> Unit
            }
        }
        item {
            HelpCenterCard(
                onButtonClick = {
                    profileViewModel.handleEvent(ProfileViewEvent.OnSupportClicked)
                }
            )
        }
        item {
            DeleteAccountCard(
                onDeleteAccountClicked = {
                    profileViewModel.handleEvent(OnDeleteAccountCardClicked(profileViewModel.uid.value))
                }
            )
        }
        item {
            AppNameInfoCard(onSignOutClicked = { profileViewModel.handleEvent(OnSignOutClicked) })
        }
    }

    HandleNavigationEffect(profileViewModel) { effect ->
        when (effect) {
            is NavigateToLanding -> onNavigateToLanding()
            OpenEmailApp -> shouldGoToEmailApp.value = true
        }
    }

    when (state) {
        is ProfileState.Error -> onError((state as ProfileState.Error).message)
        else -> Unit
    }

    if (shouldGoToEmailApp.value) {
        SendMail(
            to = Res.string.contact_email.key,
            subject = Res.string.support_request.key
        )
    }
}

@Composable
private fun AppNameInfoCard(
    onSignOutClicked: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Currency Cap v1.0.0",
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Text(
            text = "© 2024 Currency Cap. All rights reserved.",
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(32.dp))

        SecondaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Sign Out",
            onButtonClick = onSignOutClicked
        )
    }
}
