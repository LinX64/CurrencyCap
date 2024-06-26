package com.client.currencycap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun MyComposeApp() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF212121)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .width(100.dp)
                .height(300.dp)
                .background(Color.DarkGray, shape = CircleShape)
        ) {
            //IconItem(
//                icon = painterResource(id = R.drawable.ic_top_icon),
//                contentDescription = "Top Icon"
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            HighlightedIconItem(
//                icon = painterResource(id = R.drawable.ic_bitcoin),
//                contentDescription = "Bitcoin Icon"
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            IconItem(
//                icon = painterResource(id = R.drawable.ic_bottom_icon),
//                contentDescription = "Bottom Icon"
//            )
        }
    }
}

@Composable
fun IconItem(icon: Painter, contentDescription: String) {
    Icon(
        painter = icon,
        contentDescription = contentDescription,
        modifier = Modifier.size(50.dp),
        tint = Color.Gray
    )
}

@Composable
fun HighlightedIconItem(icon: Painter, contentDescription: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(70.dp)
            .clip(CircleShape)
            .background(Color(0xFFFFD700))
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(50.dp),
            tint = Color.White
        )
    }
}

@Composable
fun DefaultPreview() {
    MyComposeApp()
}