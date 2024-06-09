package ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.screens.profile.components.DeleteAccountCard
import ui.screens.profile.components.HelpCenterCard
import ui.screens.profile.components.ProfileCard

@Composable
internal fun ProfileScreen(
    padding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = padding
    ) {
        item { ProfileCard() }
        item { HelpCenterCard() }
        item { DeleteAccountCard() }
    }
}