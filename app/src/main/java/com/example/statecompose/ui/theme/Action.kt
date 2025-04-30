package com.example.statecompose.ui.theme

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

@Composable
fun Modifier.boundedClickable(onClick: () -> Unit) = clickable(
    interactionSource = remember { MutableInteractionSource() },
    indication = ripple(bounded = true),
    onClick = onClick
)
