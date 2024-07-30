package ui.screens.initial.get_verified

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mvicompose.linx64.ui.components.HandleNavigationEffect
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.finish_sign_up
import currencycap.composeapp.generated.resources.get_verified
import currencycap.composeapp.generated.resources.we_have_sent_code
import di.koinViewModel
import org.jetbrains.compose.resources.stringResource
import ui.components.base.BaseCenterColumn
import ui.components.base.button.PrimaryButton
import ui.screens.initial.get_verified.GetVerifiedPhoneNavigationEffect.NavigateToMarketOverview
import ui.screens.initial.get_verified.GetVerifiedPhoneViewEvent.OnCodeChanged
import ui.screens.initial.get_verified.GetVerifiedPhoneViewEvent.OnFinishSignUpClick
import ui.screens.initial.get_verified.components.CodeTextField
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_32
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun GetVerifiedPhoneScreen(
    padding: PaddingValues = PaddingValues(SPACER_PADDING_16),
    getVerifiedPhoneViewModel: GetVerifiedPhoneViewModel = koinViewModel<GetVerifiedPhoneViewModel>(),
    navigateToMarketOverview: () -> Unit,
    onError: (message: String) -> Unit
) {
    val state by getVerifiedPhoneViewModel.viewState.collectAsStateWithLifecycle()
    BaseCenterColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        GetVerifiedPhoneForm(
            onFinishSignUpClick = { getVerifiedPhoneViewModel.handleEvent(OnFinishSignUpClick) },
            onCodeChange = { code -> getVerifiedPhoneViewModel.handleEvent(OnCodeChanged(code)) },
            onError = onError
        )
    }

    HandleNavigationEffect(viewModel = getVerifiedPhoneViewModel) { effect ->
        when (effect) {
            is NavigateToMarketOverview -> navigateToMarketOverview()
        }
    }

    when (state) {
        is GetVerifiedPhoneState.Error -> onError((state as GetVerifiedPhoneState.Error).message)
        else -> Unit
    }
}

@Composable
private fun GetVerifiedPhoneForm(
    modifier: Modifier = Modifier,
    onCodeChange: (String) -> Unit,
    onFinishSignUpClick: () -> Unit,
    onError: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val code = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SPACER_PADDING_32),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        Text(
            text = stringResource(Res.string.get_verified),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_8))

        Text(
            text = stringResource(Res.string.we_have_sent_code),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_32))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CodeTextField(
                value = code.value,
                onValueChange = {
                    code.value = it
                    onCodeChange(it)
                },
                onError = {
                    keyboardController?.hide()
                    onError(it)
                }
            )

            Spacer(modifier = modifier.height(SPACER_PADDING_32))

            PrimaryButton(
                text = stringResource(Res.string.finish_sign_up),
                onButtonClick = onFinishSignUpClick
            )
        }
    }
}
