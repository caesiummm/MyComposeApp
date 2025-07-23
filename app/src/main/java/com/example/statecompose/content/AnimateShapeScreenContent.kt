@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.statecompose.content

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.statecompose.R

private val duration = 30000
const val KEY_IMAGE = "selected_image"
const val KEY_BACKGROUND_CONTAINER = "background_container"

data class DisplayItem(val portrait: String?, val name: String, val portraitColor: Color)

data class ListState(
    val isExpanded: Boolean = false,
    val selectedItemIndex: Int = -1, // -1 means no selection (no border)
    val displayedItemIndex: Int = -1, // -1 means use last item for portrait
    val items: List<DisplayItem> = emptyList()
)

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun AnimateShape2() {
    var isClicked by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .safeContentPadding()
            .padding(30.dp)
    ) {
        SharedTransitionLayout {
            AnimatedContent(
                targetState = isClicked,
                transitionSpec = { scaleIn().togetherWith(scaleOut()) }
            ) { targetState ->
                val sharedTransitionScope = this@SharedTransitionLayout
                val animatedVisibilityScope = this@AnimatedContent

                if (!targetState) {
                    with(sharedTransitionScope) {
                        val roundedCornerAnimation by animatedVisibilityScope.transition.animateDp { state: EnterExitState ->
                            when(state) {
                                EnterExitState.PreEnter -> 50.dp
                                EnterExitState.Visible -> 0.dp
                                EnterExitState.PostExit -> 0.dp
                            }
                        }
                        Box(
                            modifier = Modifier
                                .sharedBounds(
                                    sharedContentState = rememberSharedContentState(key = "test"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                    clipInOverlayDuringTransition = OverlayClip(
                                        RoundedCornerShape(
                                            roundedCornerAnimation
                                        )
                                    )
                                )
                                .size(50.dp)
                                .background(Color.Red, RoundedCornerShape(50.dp))
                                .clip(RoundedCornerShape(50.dp))
                                .clickable { isClicked = true }
                        )
                    }

                } else {
                    with(sharedTransitionScope) {
                        val roundedCornerAnimation by animatedVisibilityScope.transition.animateDp { state: EnterExitState ->
                            when(state) {
                                EnterExitState.PreEnter -> 0.dp
                                EnterExitState.Visible -> 50.dp
                                EnterExitState.PostExit -> 50.dp
                            }
                        }
                        Box(
                            modifier = Modifier
                                .sharedBounds(
                                    sharedContentState = rememberSharedContentState(key = "test"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                    clipInOverlayDuringTransition = OverlayClip(
                                        RoundedCornerShape(
                                            roundedCornerAnimation
                                        )
                                    )
                                )
                                .size(160.dp)
                                .background(Color.Blue, RoundedCornerShape(0.dp))
                                .clip(RoundedCornerShape(0.dp))
                                .clickable { isClicked = false }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnimateShapeScreenContent(
    items: List<DisplayItem>,
    modifier: Modifier = Modifier
) {
    var listState by remember {
        mutableStateOf(
            ListState(
                items = items,
                selectedItemIndex = -1,
                displayedItemIndex = -1
            )
        )
    }

    // Get the currently displayed item (based on displayedItemIndex, fallback to last)
    val currentDisplayItem = remember(listState) {
        when {
            listState.displayedItemIndex >= 0 -> listState.items.getOrNull(listState.displayedItemIndex)
            listState.items.isNotEmpty() -> listState.items.last()
            else -> null
        }
    }

    BackHandler(enabled = listState.isExpanded) {
        listState = listState.copy(isExpanded = false)
    }

    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
    ) {
        if (null != currentDisplayItem) {
            SharedTransitionLayout {
                AnimatedContent(
                    targetState = listState.isExpanded,
                    modifier = modifier,
                    label = "expandable_transition"
                ) { isExpanded ->
                    if (!isExpanded) {
                        MinimizedView(
                            item = currentDisplayItem,
                            onClick = {
                                listState = listState.copy(isExpanded = true)
                            },
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedVisibilityScope = this@AnimatedContent
                        )
                    } else {
                        ExpandedView(
                            items = listState.items,
                            selectedIndex = listState.selectedItemIndex,
                            currentDisplayItem = currentDisplayItem,
                            onItemClick = { index ->
                                listState = if (index == listState.selectedItemIndex) {
                                    // Clicking on already selected item - deselect but keep as displayed
                                    listState.copy(
                                        selectedItemIndex = -1,
                                        displayedItemIndex = index,
                                        isExpanded = false
                                    )
                                } else {
                                    // Clicking on different item - select and display it
                                    listState.copy(
                                        selectedItemIndex = index,
                                        displayedItemIndex = index,
                                        isExpanded = false
                                    )
                                }
                            },
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedVisibilityScope = this@AnimatedContent
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MinimizedView(
    item: DisplayItem,
    onClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .sharedElement(
                    sharedContentState = rememberSharedContentState(key = KEY_BACKGROUND_CONTAINER),
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .clip(CircleShape)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = item.name,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(color = item.portraitColor, shape = CircleShape)
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = KEY_IMAGE),
                        animatedVisibilityScope = animatedVisibilityScope
                    ),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.8f))
            )
        }
    }
}

@Composable
private fun ExpandedView(
    items: List<DisplayItem>,
    selectedIndex: Int,
    currentDisplayItem: DisplayItem?,
    onItemClick: (Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        Column(
            modifier = Modifier
                .sharedElement(
                    sharedContentState = rememberSharedContentState(key = KEY_BACKGROUND_CONTAINER),
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .wrapContentSize()
                .width(IntrinsicSize.Max)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items.forEachIndexed { index, item ->
                // Determine if this item should show the shared element based on displayedItemIndex
                val shouldShowSharedElement = currentDisplayItem != null && item.name == currentDisplayItem.name
                
                ItemRow(
                    item = item,
                    isSelected = selectedIndex == index,
                    shouldShowSharedElement = shouldShowSharedElement,
                    onClick = { onItemClick(index) },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope
                )
            }
        }
    }
}

@Composable
private fun ItemRow(
    item: DisplayItem,
    isSelected: Boolean,
    shouldShowSharedElement: Boolean,
    onClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CircleShape)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
                .then(
                    if (isSelected) {
                        Modifier.border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                    } else Modifier
                )
                .clickable { onClick() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = item.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(item.portraitColor)
                        .then(
                            if (shouldShowSharedElement) {
                                Modifier.sharedElement(
                                    sharedContentState = rememberSharedContentState(key = KEY_IMAGE),
                                    animatedVisibilityScope = animatedVisibilityScope
                                )
                            } else Modifier
                        ),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.8f))
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}

@Preview
@Composable
fun ExampleUsage() {
    val sampleItems = listOf(
        DisplayItem(null, "Buz AI", Color(0xFFE3F2FD)),
        DisplayItem(null, "Riddle Pal", Color(0xFFFCE4EC)),
        DisplayItem(null, "Psychologist Bot", Color(0xFFF3E5F5)),
        DisplayItem(null, "Translator", Color(0xFFE8F5E8))
    )

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimateShapeScreenContent(items = sampleItems)
            }
        }
    }
}