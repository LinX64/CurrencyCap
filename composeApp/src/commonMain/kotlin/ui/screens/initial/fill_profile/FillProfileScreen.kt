package ui.screens.initial.fill_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.fill_profile
import currencycap.composeapp.generated.resources.finish_sign_up
import currencycap.composeapp.generated.resources.please_fill_in_your_full_name
import currencycap.composeapp.generated.resources.skip
import di.koinViewModel
import org.jetbrains.compose.resources.stringResource
import ui.components.base.BaseCenterColumn
import ui.components.base.HandleNavigationEffect
import ui.components.base.button.PrimaryButton
import ui.components.base.button.SecondaryButton
import ui.screens.initial.fill_profile.FillProfileNavigationEffect.NavigateToMarketOverview
import ui.screens.initial.fill_profile.FillProfileViewEvent.OnNameChanged
import ui.screens.initial.fill_profile.FillProfileViewEvent.OnPhoneNumberChanged
import ui.screens.initial.fill_profile.FillProfileViewEvent.OnSignUpClick
import ui.screens.initial.fill_profile.components.NameTextField
import ui.screens.initial.fill_profile.components.PhoneNumberTextField
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_32

@Composable
internal fun FillProfileScreen(
    fillProfileViewModel: FillProfileViewModel = koinViewModel<FillProfileViewModel>(),
    navigateToMarketOverview: () -> Unit,
    onError: (message: String) -> Unit
) {
    val state by fillProfileViewModel.viewState.collectAsStateWithLifecycle()
    BaseCenterColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(SPACER_PADDING_16),
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
            .padding(horizontal = SPACER_PADDING_16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        Text(
            text = stringResource(Res.string.fill_profile),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(Res.string.please_fill_in_your_full_name),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_32))

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

            Spacer(modifier = modifier.height(SPACER_PADDING_32))

            PrimaryButton(
                text = stringResource(Res.string.finish_sign_up),
                onButtonClick = onFinishSignUpClick
            )

            Spacer(modifier = modifier.height(SPACER_PADDING_16))

            SecondaryButton(
                onButtonClick = onSkipClick,
                text = stringResource(Res.string.skip)
            )
        }
    }
}
