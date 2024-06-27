package ui.screens.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ui.components.BlurColumn
import ui.components.DottedShape
import ui.screens.profile.components.HelpCenterItem

@Composable
internal fun SettingsGeneralCard() {
    BlurColumn {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(20.dp)
                        .background(Color(0xFF1E88E5))
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Help center",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                HelpCenterItem(text = "FAQ")
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color.Gray, shape = DottedShape(step = 10.dp))
                )

                Spacer(modifier = Modifier.height(8.dp))
                HelpCenterItem(text = "Support")
            }
        }
    }
}
