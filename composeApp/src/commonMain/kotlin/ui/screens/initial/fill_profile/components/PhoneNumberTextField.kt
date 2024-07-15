package ui.screens.initial.fill_profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ui.theme.AppDimensions.CARD_CORNER_RADIUS

@Composable
internal fun PhoneNumberTextField(
    modifier: Modifier = Modifier,
    onPhoneChanged: (String) -> Unit,
    onError: (String) -> Unit
) {
    val phone = rememberSaveable { mutableStateOf("") }
    val containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    TextField(
        modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(CARD_CORNER_RADIUS)),
        value = phone.value,
        onValueChange = {
            if (it.length <= 10) {
                phone.value = it
                onPhoneChanged(it)
            } else onError("Phone number should be 10 digits")
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