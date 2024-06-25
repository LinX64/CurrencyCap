package ui.screens.auth.fill_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import di.koinViewModel
import ui.components.BaseCenterColumn
import ui.components.HandleNavigationEffect
import ui.screens.auth.fill_profile.FillProfileNavigationEffect.NavigateToMarketOverview
import ui.screens.auth.fill_profile.FillProfileViewEvent.OnNameChanged
import ui.screens.auth.fill_profile.FillProfileViewEvent.OnPhoneNumberChanged
import ui.screens.auth.fill_profile.FillProfileViewEvent.OnSignUpClick
import ui.screens.auth.fill_profile.FillProfileViewEvent.OnSkipClicked
import ui.screens.auth.fill_profile.components.NameTextField
import ui.screens.auth.fill_profile.components.PhoneNumberTextField

@Composable
internal fun FillProfileScreen(
    padding: PaddingValues,
    fillProfileViewModel: FillProfileViewModel = koinViewModel<FillProfileViewModel>(),
    navigateToMarketOverview: () -> Unit,
    onError: (message: String) -> Unit
) {
    val state by fillProfileViewModel.viewState.collectAsState()
    BaseCenterColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        FillProfileForm(
            onNameChanged = { fillProfileViewModel.handleEvent(OnNameChanged(it)) },
            onFinishSignUpClick = { fillProfileViewModel.handleEvent(OnSignUpClick) },
            onSkipClick = { fillProfileViewModel.handleEvent(OnSkipClicked) },
            onPhoneNumberChanged = { fillProfileViewModel.handleEvent(OnPhoneNumberChanged(it)) }
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
    onPhoneNumberChanged: (String) -> Unit,
    onFinishSignUpClick: () -> Unit,
    onSkipClick: () -> Unit
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
            text = "Please fill in your name and phone number",
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
                onPhoneChanged = onPhoneNumberChanged
            )

            Spacer(modifier = modifier.height(32.dp))

            Button(
                onClick = onFinishSignUpClick,
                modifier = modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    text = "Finish Sign Up",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            }

            Spacer(modifier = modifier.height(16.dp))

            Button(
                onClick = onSkipClick,
                modifier = modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Text(
                    text = "Skip",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
