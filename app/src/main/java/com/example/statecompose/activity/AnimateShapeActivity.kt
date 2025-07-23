package com.example.statecompose.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import com.example.statecompose.content.AnimateShapeScreenContent
import com.example.statecompose.content.DisplayItem
import com.example.statecompose.ui.theme.MyComposeAppTheme

class AnimateShapeActivity : BaseComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeAppTheme {
                val sampleItems = listOf(
                    DisplayItem(null, "John Doe", Color(0xFFE3F2FD)),
                    DisplayItem(null, "Jane Smith", Color(0xFFFCE4EC)),
                    DisplayItem(null, "Bob Johnson", Color(0xFFF3E5F5)),
                    DisplayItem(null, "Alice Brown", Color(0xFFE8F5E8))
                )
                AnimateShapeScreenContent(
                    items = sampleItems
                )
            }
        }
    }
}