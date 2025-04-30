package com.example.statecompose.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.statecompose.content.UpdateName
import com.example.statecompose.content.TimerDemo

class LaunchedEffectActivity : BaseComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffectNavigation()
        }
    }
}

@Composable
private fun LaunchedEffectNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LaunchedEffectScreen.LaunchedEffectHomeScreen.route
    ) {
        composable(route = LaunchedEffectScreen.LaunchedEffectHomeScreen.route) {
            LaunchedEffectScreen(navController = navController)
        }
        composable(LaunchedEffectScreen.UpdateNameScreen.route) {
            UpdateNameScreen()
        }
        composable(LaunchedEffectScreen.TimerDemoScreen.route) {
            TimerDemoScreen()
        }
    }
}

@Composable
fun LaunchedEffectScreen(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = { navController.navigate(LaunchedEffectScreen.UpdateNameScreen.route) }) {
            Text("1. Update Name")
        }
        Button(onClick = { navController.navigate(LaunchedEffectScreen.TimerDemoScreen.route) }) {
            Text("2. Timer Demo")
        }
    }
}

@Composable
private fun UpdateNameScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        UpdateName()
    }
}

@Composable
private fun TimerDemoScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        TimerDemo()
    }
}

sealed class LaunchedEffectScreen(val route: String) {
    data object LaunchedEffectHomeScreen : LaunchedEffectScreen("launched_effect_home_screen")
    data object UpdateNameScreen : LaunchedEffectScreen("update_name_screen")
    data object TimerDemoScreen : LaunchedEffectScreen("timer_demo_screen")
}