package com.client.currencycap

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

//@Composable
//@Preview(showBackground = true, device = "id:pixel_3a")
//private fun CardPreview() {
//    val hazeState = remember { HazeState() }
//    KoinPreview {
//        DarkBackground {
//            OverviewScreen(
//                modifier = Modifier.fillMaxSize(),
//                padding = PaddingValues(0.dp),
//                hazeState = hazeState,
//            )
//        }
//    }
//}

@Composable
@Preview(showBackground = true, device = "id:pixel_3a")
private fun ExchangePreview() {
    KoinPreview {
        DarkBackground {
        }
    }
}

