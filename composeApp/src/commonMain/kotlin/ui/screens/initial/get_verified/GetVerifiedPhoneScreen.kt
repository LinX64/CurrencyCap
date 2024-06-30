package ui.screens.initial.get_verified

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import di.koinViewModel
import ui.components.BaseCenterColumn
import ui.components.HandleNavigationEffect
import ui.screens.initial.get_verified.GetVerifiedPhoneNavigationEffect.NavigateToMarketOverview
import ui.screens.initial.get_verified.GetVerifiedPhoneViewEvent.OnCodeChanged
import ui.screens.initial.get_verified.GetVerifiedPhoneViewEvent.OnFinishSignUpClick
import ui.screens.initial.get_verified.components.CodeTextField

@Composable
internal fun GetVerifiedPhoneScreen(
    padding: PaddingValues,
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
            onCodeChange = { code -> getVerifiedPhoneViewModel.handleEvent(OnCodeChanged(code)) }
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
    onFinishSignUpClick: () -> Unit
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
            text = "Enter your Code",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "We have sent a code to your phone number",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CodeTextField(
                value = "",
                onValueChange = onCodeChange,
                length = 6
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
        }
    }
}
