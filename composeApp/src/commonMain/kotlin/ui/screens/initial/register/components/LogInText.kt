package ui.screens.initial.register.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
internal fun LogInText(
    onLogInClick: () -> Unit
) {
    val annotatedString = buildAnnotatedString {
        append("Already have an account? ")
        pushStringAnnotation(tag = "LogIn", annotation = "LogIn")
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
            append("Log in")
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onSurface),
        modifier = Modifier.padding(top = 16.dp),
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "LogIn", start = offset, end = offset)
                .firstOrNull()?.let {
                    onLogInClick()
                }
        }
    )
}