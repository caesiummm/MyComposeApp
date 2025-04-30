package com.example.statecompose.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
private fun PreviewContent() {
    Box(
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxSize()
            .padding(20.dp)
    ) {
        CancelButton(Modifier.align(Alignment.TopStart))
        DisplaySendToText(Modifier.align(Alignment.TopCenter))
    }
}

@Preview
@Composable
private fun CancelButton(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .size(30.dp, 30.dp)
            .background(Color.LightGray, shape = RectangleShape)
    ) {

    }
}

@Composable
private fun DisplaySendToText(modifier: Modifier = Modifier) {
    Text(
        text = "To Buz",
        fontSize = 20.sp,
        color = Color.White,
        modifier = modifier
    )
}