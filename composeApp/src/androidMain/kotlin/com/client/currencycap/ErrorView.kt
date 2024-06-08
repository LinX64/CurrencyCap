package com.client.currencycap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ErrorViewScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Oops! Something went wrong",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Please try again ...",
            style = MaterialTheme.typography.bodyLarge
        )

//        IconButton(onClick = { /*TODO*/ }) {
//            Image(
//                painter = painterResource(Res.drawable.my_icon),
//                contentDescription = "Refresh"
//            )
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorViewPreview() {
    ErrorViewScreen()
}