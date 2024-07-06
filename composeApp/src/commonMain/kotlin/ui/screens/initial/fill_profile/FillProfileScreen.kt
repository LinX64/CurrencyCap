package ui.screens.initial.fill_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import di.koinViewModel
import ui.components.BaseCenterColumn
import ui.components.HandleNavigationEffect
import ui.components.PrimaryButton
import ui.components.SecondaryButton
import ui.screens.initial.fill_profile.FillProfileNavigationEffect.NavigateToMarketOverview
import ui.screens.initial.fill_profile.FillProfileViewEvent.OnNameChanged
import ui.screens.initial.fill_profile.FillProfileViewEvent.OnPhoneNumberChanged
import ui.screens.initial.fill_profile.FillProfileViewEvent.OnSignUpClick
import ui.screens.initial.fill_profile.components.NameTextField
import ui.screens.initial.fill_profile.components.PhoneNumberTextField

@Composable
internal fun FillProfileScreen(
    padding: PaddingValues,
    fillProfileViewModel: FillProfileViewModel = koinViewModel<FillProfileViewModel>(),
    navigateToMarketOverview: () -> Unit,
    onError: (message: String) -> Unit
) {
    val state by fillProfileViewModel.viewState.collectAsStateWithLifecycle()
    BaseCenterColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        FillProfileForm(
            onSkipClick = navigateToMarketOverview,
            onError = onError,
            onNameChanged = { fillProfileViewModel.handleEvent(OnNameChanged(it)) },
            onPhoneChanged = { fillProfileViewModel.handleEvent(OnPhoneNumberChanged(it)) },
            onFinishSignUpClick = { fillProfileViewModel.handleEvent(OnSignUpClick) }
        )
    }

    HandleNavigationEffect(viewModel = fillProfileViewModel) { effect ->
        when (effect) {
            is NavigateToMarketOverview -> navigateToMarketOverview()
        }
    }

    when (state) {
        is FillProfileState.Error -> onError((state as FillProfileState.Error).message)
        else -> Unit
    }
}

@Composable
private fun FillProfileForm(
    modifier: Modifier = Modifier,
    onNameChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onFinishSignUpClick: () -> Unit,
    onSkipClick: () -> Unit,
    onError: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Fill Profile",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Please fill in your Full Name and Phone number",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NameTextField(onNameChanged = onNameChanged)

            Spacer(modifier = modifier.height(10.dp))

            PhoneNumberTextField(
                onPhoneChanged = onPhoneChanged,
                onError = onError
            )

            Spacer(modifier = modifier.height(32.dp))

            PrimaryButton(
                onButtonClick = onFinishSignUpClick,
                text = "Verify Phone Number"
            )

            Spacer(modifier = modifier.height(16.dp))

            SecondaryButton(
                onButtonClick = onSkipClick,
                text = "Skip"
            )
        }
    }
}
