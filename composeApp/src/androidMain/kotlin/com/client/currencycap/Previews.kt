package com.client.currencycap

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.chrisbanes.haze.HazeState
import ui.components.main.BottomNavigationBar

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true, device = "id:pixel_3a")
private fun BottomBarPreview() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val hazeState = HazeState()
    KoinPreview {
        DarkBackground {
            BottomNavigationBar(
                onTabSelected = { },
                scrollBehavior = scrollBehavior,
                hazeState = hazeState
            )
        }
    }
}