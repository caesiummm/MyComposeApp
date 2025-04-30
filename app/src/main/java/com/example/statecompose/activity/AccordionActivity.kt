package com.example.statecompose.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.statecompose.content.AccordionScreenContent
import com.example.statecompose.ui.theme.MyComposeAppTheme

class AccordionActivity : BaseComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeAppTheme {
                AccordionScreen()
            }
        }
    }
}

@Preview
@Composable
private fun AccordionScreen() {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onSecondary)
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        AccordionScreenContent(true)
    }
}