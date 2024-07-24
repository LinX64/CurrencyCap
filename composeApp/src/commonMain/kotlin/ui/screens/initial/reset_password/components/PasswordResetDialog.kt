package ui.screens.initial.reset_password.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.email
import currencycap.composeapp.generated.resources.email_sent
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.theme.AppDimensions.CARD_CORNER_RADIUS
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun PasswordResetDialog(
    showDialog: Boolean = true,
    onOkClick: () -> Unit
) {
    val hazeState = remember { HazeState() }
    if (showDialog) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            Surface(
                modifier = Modifier
                    .hazeChild(
                        state = hazeState,
                        shape = RoundedCornerShape(CARD_CORNER_RADIUS)
                    ),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                Column(
                    modifier = Modifier
                        .padding(SPACER_PADDING_16)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {

                    Column(
                        modifier = Modifier
                            .padding(SPACER_PADDING_16)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.email_sent),
                            contentDescription = stringResource(Res.string.email),
                            modifier = Modifier.size(64.dp)
                        )

                        Spacer(modifier = Modifier.height(SPACER_PADDING_8))

                        Text(
                            modifier = Modifier
                                .padding(SPACER_PADDING_16)
                                .fillMaxWidth(),
                            text = "Reset password email was successfully sent! Please check your email.",
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(SPACER_PADDING_16))

                    TextButton(
                        onClick = onOkClick
                    ) {
                        Text(
                            text = "OK",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}