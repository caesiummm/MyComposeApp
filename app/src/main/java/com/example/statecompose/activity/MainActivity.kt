package com.example.statecompose.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.statecompose.R
import com.example.statecompose.ui.theme.MyComposeAppTheme
import com.example.statecompose.ui.theme.boundedClickable

class MainActivity : BaseComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        item {
            MainMenuButton(text = stringResource(R.string.wellness_screen_menu_title)) {
                context.startActivity(Intent(context, WellnessActivity::class.java))
            }
        }
        item {
            MainMenuButton(text = stringResource(R.string.launched_effect_screen_menu_title)) {
                context.startActivity(Intent(context, LaunchedEffectActivity::class.java))
            }
        }
        item {
            MainMenuButton(text = stringResource(R.string.multi_panel_menu_title)) {
                context.startActivity(Intent(context, BottomMenuPanelActivity::class.java))
            }
        }
        item {
            MainMenuButton(text = stringResource(R.string.accordion_menu_title)) {
                context.startActivity(Intent(context, AccordionActivity::class.java))
            }
        }
        item {
            MainMenuButton(text = stringResource(R.string.basic_layout_menu_title)) {
                context.startActivity(Intent(context, BasicLayoutActivity::class.java))
            }
        }
    }
}

@Composable
fun MainMenuButton(text: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFDEE9FA),
        shadowElevation = 1.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .boundedClickable { onClick() }
                .padding(16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
         