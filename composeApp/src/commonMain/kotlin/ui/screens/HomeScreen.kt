package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun HomeRoute() {
    HomeScreen()
}

@Composable
internal fun HomeScreen() {
    Column {
        Text(text = "dsdsd")
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}