package ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import di.koinViewModel
import ui.components.CenteredColumn
import ui.components.HandleNavigationEffect
import ui.screens.profile.ProfileNavigationEffect.NavigateToLanding
import ui.screens.profile.ProfileViewEvent.OnDeleteAccountCardClicked
import ui.screens.profile.components.DeleteAccountCard
import ui.screens.profile.components.HelpCenterCard
import ui.screens.profile.components.ProfileCard

@Composable
internal fun ProfileScreen(
    profileViewModel: ProfileViewModel = koinViewModel<ProfileViewModel>(),
    padding: PaddingValues,
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit
) {
    val state by profileViewModel.viewState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = padding
    ) {
        item {
            when (state) {
                is ProfileState.Success -> {
                    val profileState = state as ProfileState.Success
                    ProfileCard(
                        name = profileState.user.name ?: "",
                        email = profileState.user.email ?: "",
                        phone = profileState.user.phoneNumber ?: "",
                    )
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