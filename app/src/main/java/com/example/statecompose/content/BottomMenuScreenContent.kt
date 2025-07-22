package com.example.statecompose.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.statecompose.R

enum class PanelType {
    First, Second, Third
}

@Composable
fun StatefulButtonState() {
    // Keep track of the currently selected panel
    val selectedPanel = rememberSaveable { mutableStateOf<PanelType?>(null) }

    MenuContent(
        selectedPanel = selectedPanel,
        onSelectPanel = { panelType ->
            // Toggle the panel if it's already selected, otherwise select it
            selectedPanel.value = if (selectedPanel.value == panelType) null else panelType
        }
    )
}

@Composable
fun MenuContent(selectedPanel: State<PanelType?>, onSelectPanel: (PanelType) -> Unit) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
    ) {
        // Menu panel with buttons is always visible at the bottom
        MenuPanel(selectedPanel, onSelectPanel)

        // Show the appropriate panel based on selection
        AnimatedVisibility(
            visible = selectedPanel.value == PanelType.First,
            enter = fadeIn(animationSpec = tween(300)) + expandVertically(
                animationSpec = tween(300),
                expandFrom = Alignment.Top
            ),
            exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(
                animationSpec = tween(300),
                shrinkTowards = Alignment.Top
            )
        ) {
            FirstPanel()
        }
        AnimatedVisibility(
            visible = selectedPanel.value == PanelType.Second,
            enter = fadeIn(animationSpec = tween(300)) + expandVertically(
                animationSpec = tween(300),
                expandFrom = Alignment.Top
            ),
            exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(
                animationSpec = tween(300),
                shrinkTowards = Alignment.Top
            )
        ) {
            SecondPanel()
        }
        AnimatedVisibility(
            visible = selectedPanel.value == PanelType.Third,
            enter = fadeIn(animationSpec = tween(300)) + expandVertically(
                animationSpec = tween(300),
                expandFrom = Alignment.Top
            ),
            exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(
                animationSpec = tween(300),
                shrinkTowards = Alignment.Top
            )
        ) {
            ThirdPanel()
        }
    }
}

@Composable
private fun MenuPanel(selectedPanel: State<PanelType?>, onSelectPanel: (PanelType) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = Modifier
            .background(colorResource(R.color.neutral_20))
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 12.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FirstButton(
            isSelected = remember { derivedStateOf { selectedPanel.value == PanelType.First } },
            onSelect = {
                keyboardController?.hide()
                onSelectPanel(PanelType.First)
            }
        )
        CustomTextField()
        SecondButton(
            isSelected = remember { derivedStateOf { selectedPanel.value == PanelType.Second } },
            onSelect = {
                keyboardController?.hide()
                onSelectPanel(PanelType.Second)
            }
        )
        ThirdButton(
            isSelected = remember { derivedStateOf { selectedPanel.value == PanelType.Third } },
            onSelect = {
                keyboardController?.hide()
                onSelectPanel(PanelType.Third)
            }
        )
    }
}

@Preview
@Composable
private fun FirstPanel() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Text(text = "First Panel", fontSize = 30.sp)
    }
}

@Composable
private fun SecondPanel() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color.Blue)
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Text(text = "Second Panel", fontSize = 30.sp)
    }
}

@Composable
private fun ThirdPanel() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color.Green)
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Text(text = "Third Panel", fontSize = 30.sp)
    }
}

@Composable
private fun FirstButton(isSelected: State<Boolean>, onSelect: () -> Unit) {
    MenuButton("A", isSelected, onSelect)
}

@Composable
private fun SecondButton(isSelected: State<Boolean>, onSelect: () -> Unit) {
    MenuButton("B", isSelected, onSelect)
}

@Composable
private fun ThirdButton(isSelected: State<Boolean>, onSelect: () -> Unit) {
    MenuButton("C", isSelected, onSelect)
}

@Composable
private fun CustomTextField() {
    var text by remember { mutableStateOf("") }
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .width(200.dp)
    ) {
        BasicTextField(
            value = text,
            onValueChange = { text = it },
            singleLine = true,
            textStyle = TextStyle(fontSize = 14.sp),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
        )
        if (text.isEmpty()) {
            Text(
                text = "Enter text",
                color = Color.Black,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun MenuButton(text: String, isSelected: State<Boolean>, onSelect: () -> Unit) {
    val animatedColorState by animateColorAsState(
        targetValue = if (isSelected.value) Color.DarkGray else Color.LightGray,
        animationSpec = tween(300),
        label = "MenuButtonAnimatedColor"
    )
    Surface(
        selected = isSelected.value,
        onClick = onSelect,
        shape = CircleShape,
        color = animatedColorState,
        modifier = Modifier.size(40.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = text)
        }
    }
}