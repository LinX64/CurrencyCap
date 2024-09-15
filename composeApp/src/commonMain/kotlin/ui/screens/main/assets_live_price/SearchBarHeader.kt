package ui.screens.main.assets_live_price

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ui.theme.AppDimensions.CARD_CORNER_RADIUS

@Composable
internal fun SearchBarHeader(
    modifier: Modifier,
    onValueChange: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(CARD_CORNER_RADIUS),
    ) {
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onValueChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                IconButton(onClick = { searchQuery = "" }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear search"
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
    }
}