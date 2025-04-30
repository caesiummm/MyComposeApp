package com.example.statecompose.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * In singleExpandMode, only one item can be expanded at a time. Elsewhere, multiple items can be expanded.
 */
@Composable
fun AccordionScreenContent(singleExpandMode: Boolean = false) {
    // Track the currently expanded item index for single expand mode
    val expandedItemIndex = rememberSaveable { mutableIntStateOf(-1) }
    val items = List(30) { index ->
        "Item ${index + 1}"
    }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(count = items.size) { index ->
            // Create a persistent state object for this item
            val expanded = rememberSaveable { mutableStateOf(false) }

            // Calculate if this item should be expanded
            if (singleExpandMode) {
                // In single expand mode, update the state to match the expandedItemIndex
                expanded.value = (index == expandedItemIndex.intValue)
            }

            AccordionItem(
                title = items[index],
                expanded = expanded, // Pass the actual state object, not a new one
                onItemClick = {
                    if (singleExpandMode) {
                        // If this item is already expanded, collapse it by setting index to -1
                        // Otherwise, expand this item and collapse others
                        expandedItemIndex.intValue = if (expanded.value) -1 else index
                    } else {
                        // Toggle in multiple expand mode
                        expanded.value = !expanded.value
                    }
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
private fun AccordionItem(
    title: String,
    expanded: State<Boolean>,
    onItemClick: () -> Unit = {}
) {
    // Read the expanded value once to minimize recompositions
    val isExpanded = expanded.value
    val itemContainerColor by animateColorAsState(
        targetValue =
            if (isExpanded) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.secondaryContainer,
        label = "Item Background Color Change"
    )

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        // Extract header to its own composable with stable props
        AccordionHeader(
            title = title,
            isExpanded = isExpanded,
            itemContainerColor = itemContainerColor,
            onItemClick = onItemClick
        )

        // Extract content to its own composable
        AccordionContent(
            isExpanded = isExpanded,
            itemContainerColor = itemContainerColor
        )
    }
}

@Composable
private fun AccordionHeader(
    title: String,
    isExpanded: Boolean,
    itemContainerColor: Color,
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .background(color = itemContainerColor)
            .padding(horizontal = 18.dp, vertical = 12.dp)
    ) {
        val titleSelectedColor by animateColorAsState(
            targetValue =
                if (isExpanded) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSecondaryContainer,
            label = "Title Color Change"
        )

        Text(
            text = title,
            fontSize = 18.sp,
            color = titleSelectedColor,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        val rotationDegree by animateFloatAsState(
            targetValue = if (isExpanded) 180f else 0f,
            label = "Arrow Rotation",
        )

        Icon(
            imageVector = Icons.Rounded.KeyboardArrowDown,
            tint = titleSelectedColor,
            contentDescription = if (isExpanded) "Collapse" else "Expand",
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = rotationDegree
                }
                .size(30.dp)
                .align(Alignment.CenterEnd)
        )
    }
}

@Composable
private fun AccordionContent(
    isExpanded: Boolean,
    itemContainerColor: Color
) {
    AnimatedVisibility(
        visible = isExpanded,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = itemContainerColor)
        ) {
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus lacinia odio vitae vestibulum vestibulum.\n",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 14.sp,
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}