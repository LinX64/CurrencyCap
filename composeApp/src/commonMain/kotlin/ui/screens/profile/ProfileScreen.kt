package ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.screens.profile.components.DeleteAccountCard
import ui.screens.profile.components.FirstCard
import ui.screens.profile.components.HelpCenterCard


@Composable
internal fun ProfileScreen(
    padding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ProfileCard()
        }
    }
}

@Composable
internal fun ProfileCard(
    modifier: Modifier = Modifier,
    profileImage: String = "https://www.w3schools.com/howto/img_avatar.png",
    name: String = "John Doe",
    email: String = "ash.qxrz@hotmail.com",
    phone: String = "+989123456789"
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            FirstCard(
                profileImage = profileImage,
                name = name,
                email = email,
                phone = phone
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            HelpCenterCard()
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            DeleteAccountCard()
        }
    }
}
