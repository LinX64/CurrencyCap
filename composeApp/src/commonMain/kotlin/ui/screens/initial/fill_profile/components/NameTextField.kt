package ui.screens.initial.fill_profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.full_name
import org.jetbrains.compose.resources.stringResource
import ui.theme.AppDimensions.CARD_CORNER_RADIUS

@Composable
internal fun NameTextField(
    modifier: Modifier = Modifier,
    onNameChanged: (String) -> Unit
) {
    val name = rememberSaveable { mutableStateOf("") }
    val containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(CARD_CORNER_RADIUS)),
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
        placeholder = { Text(text = stringResource(Res.string.full_name)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
    )
}