package ui.screens.auth.fill_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import di.koinViewModel
import ui.components.BaseCenterColumn
import ui.components.HandleNavigationEffect
import ui.screens.auth.fill_profile.FillProfileNavigationEffect.NavigateToMarketOverview
import ui.screens.auth.fill_profile.FillProfileViewEvent.EmailChanged
import ui.screens.auth.fill_profile.FillProfileViewEvent.OnSignUpClick
import ui.screens.auth.fill_profile.FillProfileViewEvent.OnSkipClicked
import ui.screens.auth.fill_profile.FillProfileViewEvent.PasswordChanged

@Composable
internal fun FillProfileScreen(
    padding: PaddingValues,
    fillProfileViewModel: FillProfileViewModel = koinViewModel<FillProfileViewModel>(),
    navigateToMarketOverview: () -> Unit,
    onError: (message: String) -> Unit
) {
    BaseCenterColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        FillProfileForm(
            onEmailChanged = { fillProfileViewModel.handleEvent(EmailChanged(it)) },
            onPasswordChanged = { fillProfileViewModel.handleEvent(PasswordChanged(it)) },
            onFinishSignUpClick = { fillProfileViewModel.handleEvent(OnSignUpClick) },
            onSkipClick = { fillProfileViewModel.handleEvent(OnSkipClicked) }
        )
    }

    HandleNavigationEffect(viewModel = fillProfileViewModel) { effect ->
        when (effect) {
            is NavigateToMarketOverview -> navigateToMarketOverview()
        }
    }
}

@Composable
private fun FillProfileForm(
    modifier: Modifier = Modifier,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onFinishSignUpClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .background(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
                NameTextField(
                    onNameChanged = onEmailChanged
                )
                Spacer(modifier = modifier.height(10.dp))

                PhoneNumberTextField(
                    onPhoneChanged = onEmailChanged
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
}

@Composable
fun NameTextField(
    modifier: Modifier = Modifier,
    onNameChanged: (String) -> Unit
) {
    val name = rememberSaveable { mutableStateOf("") }
    val containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = modifier.fillMaxWidth(),
        value = name.value,
        onValueChange = {
            name.value = it
            onNameChanged(it)
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(10.dp),
        maxLines = 1,
        leadingIcon = {
            Icon(
                modifier = modifier.padding(start = 16.dp),
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        placeholder = {
            Text(text = "Name")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
    )
}

@Composable
fun PhoneNumberTextField(
    modifier: Modifier = Modifier,
    onPhoneChanged: (String) -> Unit
) {
    val phone = rememberSaveable { mutableStateOf("") }
    val containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = phone.value,
        onValueChange = {
            phone.value = it
            onPhoneChanged(it)
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(10.dp),
        maxLines = 1,
        leadingIcon = {
            Icon(
                modifier = modifier.padding(start = 16.dp),
                imageVector = Icons.Outlined.Phone,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        placeholder = {
            Text(text = "Phone Number")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done
        )
    )
}

