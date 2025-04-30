package com.example.statecompose.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random

fun Modifier.randomColorBg() = this
    .background(
        color = randomColor(),
        shape = RoundedCornerShape(10.dp)
    )
    .padding(10.dp)

fun randomColor(): Color {
    return Color(
        red = Random.nextInt(256) / 255f,
        green = Random.nextInt(256) / 255f,
        blue = Random.nextInt(256) / 255f
    )
}