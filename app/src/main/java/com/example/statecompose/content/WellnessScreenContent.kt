package com.example.statecompose.content

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.statecompose.ui.theme.randomColor
import com.example.statecompose.ui.theme.randomColorBg

const val MAXIMUM_COUNT = 30

// State Hoisting: Moving state to a composable caller to make a composable stateless
// A stateful composable owns state (StatefulCounter)
// A stateless composable does not hold any state (WaterCounterContent)
@Composable
fun StatefulCounter() {
    val count = rememberSaveable { mutableIntStateOf(0) } // state
    // state goes down to stateless composable function
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.background(color = randomColor(), shape = RoundedCornerShape(20.dp))
    ) {
        WaterCounterContent(count = count, onUpdate = { count.intValue++ })
    }
    // event goes up to stateful composable function
}

// With "count" state hoisted, this counter can be reused with different variations of "count" (e.g. juiceCount, beerCount)
// The same "count" variable can also be reused for different Counter (e.g. juiceCounter, beerCounter)
@Composable
private fun WaterCounterContent(count: State<Int>, onUpdate: () -> Unit) {
    val textState = remember {
        derivedStateOf {
            if (count.value == MAXIMUM_COUNT) {
                "You have reached the max amount!"
            } else if (count.value == 1) {
                "You have ${count.value} glass"
            } else if (count.value > 0) {
                "You have ${count.value} glasses"
            } else {
                "Tap the button to add one glass of water!"
            }
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = randomColor(), shape = RoundedCornerShape(20.dp))
            .padding(40.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentSize()
                .background(color = randomColor(), shape = RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            WaterCounterText(textState)

            Spacer(modifier = Modifier.height(20.dp))

            CounterButton(count = count, onUpdate = onUpdate)
        }
    }
}

// Passing lambda instead of actual state value, "count"
// This Button composable that hold this lambda do not directly hold the mutable parameter, "count"
// Since the lambda is always the same function, even if the value changes, the composable holding the lambda doesn't need to recompose
@Composable
private fun CounterButton(count: State<Int>, onUpdate: () -> Unit) {
    Button(
        onClick = onUpdate,
        enabled = count.value < MAXIMUM_COUNT,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White,
            disabledContainerColor = Color.DarkGray,
            disabledContentColor = Color.Gray
        )
    ) {
        Text(text = "Add one")
    }
}

@Composable
private fun WaterCounterText(text: State<String>) {
    Text(
        text = text.value,
        modifier = Modifier
            .randomColorBg()
            .animateContentSize(animationSpec = spring()),
        color = randomColor(),
        textAlign = TextAlign.Center
    )
}