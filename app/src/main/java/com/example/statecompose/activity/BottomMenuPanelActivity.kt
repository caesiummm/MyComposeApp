package com.example.statecompose.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.statecompose.R
import com.example.statecompose.content.StatefulButtonState
import com.example.statecompose.ui.theme.MyComposeAppTheme

class BottomMenuPanelActivity: BaseComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeAppTheme {
                BottomMenuPanelScreen()
            }
        }
    }
}

@Composable
fun BottomMenuPanelScreen() {
    Box(
        modifier = Modifier.background(color = colorResource(R.color.overlay_grey_12)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "This is a Bottom Menu Panel Screen",
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            color = Color.LightGray,
        )
        StatefulButtonState()
    }
}