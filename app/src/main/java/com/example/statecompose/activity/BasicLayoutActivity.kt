package com.example.statecompose.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import com.example.statecompose.content.LayoutHomeScreenLandscape
import com.example.statecompose.content.LayoutHomeScreenPortrait
import com.example.statecompose.ui.theme.MyComposeAppTheme

class BasicLayoutActivity : BaseComposeActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeAppTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                MyLayoutDemoApp(windowSizeClass)
            }
        }
    }
}

@Composable
private fun MyLayoutDemoApp(windowSize: WindowSizeClass) {
    when(windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            LayoutHomeScreenPortrait()
        }
        WindowWidthSizeClass.Medium,
        WindowWidthSizeClass.Expanded -> {
            LayoutHomeScreenLandscape()
        }
    }
}

