package com.example.statecompose.flow

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

fun main() {
    val coldFlow = flow {
        emit(0)
        emit(1)
        emit(2)
    }

    GlobalScope.launch { // Calling collect the first time
        coldFlow.collect { value ->
            println("cold flow collector 1 received: $value")
        }

        delay(2500)

        // Calling collect a second time
        coldFlow.collect { value ->
            println("cold flow collector 2 received: $value")
        }
    }
}