package com.example.statecompose.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.statecompose.ui.theme.randomColor
import com.example.statecompose.viewmodel.EffectHandlerViewModel
import kotlinx.coroutines.delay

private const val duration = 1000

@Composable
fun UpdateName() {
    val vm: EffectHandlerViewModel = viewModel()
    val name by vm.nameState.collectAsState()
    val animatedColor by animateColorAsState(
        targetValue = randomColor(),
        animationSpec = tween(durationMillis = duration),
        label = "animateNameDisplayColor"
    )
    LaunchedEffect(key1 = Unit) {
        // Whenever key1 changes, the scope will be cancelled & re-launched
        // Without LaunchedEffect, loadName will be executed twice
        // In this case, loadName only executes once
        vm.loadName()
    }

    AnimatedVisibility(
        visible = name.isNotEmpty(),
        enter = fadeIn(animationSpec = tween(durationMillis = duration))
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .border(width = 2.dp, shape = RoundedCornerShape(20.dp), color = Color.DarkGray)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .background(animatedColor, RoundedCornerShape(20.dp))
                    .padding(16.dp)
                    .animateContentSize()
            )

            Button(onClick = { vm.changeName() }) {
                Text(text = "Change Name")
            }
        }
    }
}


@Composable
fun TimerDemo() {
    println("TimerDemo()")
    var count by rememberSaveable { mutableIntStateOf(0) }
    var timerStarts by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = timerStarts) {
        if (timerStarts) {
            startTimer(onTick = {
                count++
            })
            timerStarts = false
        }
        count = 0
    }

    Row(
        modifier = Modifier
            .width(200.dp)
            .background(color = Color.Black, shape = RoundedCornerShape(20.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            modifier = Modifier.animateContentSize(),
            onClick = { timerStarts = true }
        ) {
            Text(text = if (timerStarts) "Counting" else "Start")
        }
        TimerDisplay(count = count)
    }
}

@Composable
private fun TimerDisplay(count: Int) {
    println("Timer: count = $count")
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .background(color = randomColor(), shape = CircleShape)
    ) {
        Text(
            text = count.toString(),
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }

}

@Preview
@Composable
private fun TimerDisplayPreview() {
    TimerDisplay(5)
}

suspend fun startTimer(duration: Int = 10, onTick: () -> Unit) {
    for (i in 1..duration + 1) {
        delay(1000)
        println("Timer: $i")
        onTick.invoke()
    }
}