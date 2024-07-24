package ui.screens.main.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.profile
import data.remote.model.User
import org.jetbrains.compose.resources.stringResource
import ui.components.base.GlassCard
import ui.screens.main.overview.components.getPlaceHolder
import ui.theme.AppDimensions.SPACER_PADDING_16

@Composable
internal fun ProfileCard(
    profilePicture: String = "https://www.w3schools.com/howto/img_avatar.png",
    user: User,
    isLoading: Boolean = false
) {
    val isLoadingModifier = if (isLoading) getPlaceHolder(Modifier) else Modifier
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        GlassCard {
            Column(
                modifier = Modifier.padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    modifier = if (isLoading) getPlaceHolder(
                        Modifier.size(100.dp).clip(RoundedCornerShape(55)),
                    ) else Modifier.size(100.dp).clip(RoundedCornerShape(55)),
                    model = profilePicture,
                    contentDescription = stringResource(Res.string.profile)
                )

                Spacer(modifier = Modifier.height(SPACER_PADDING_16))

                Text(
                    modifier = isLoadingModifier,
                    text = user.fullName ?: "Unknown",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(SPACER_PADDING_16))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = isLoadingModifier,
                        text = user.email ?: "Unknown",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        modifier = isLoadingModifier,
                        text = user.phoneNumber ?: "Unknown",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}