package com.example.statecompose.content

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.statecompose.R
import com.example.statecompose.ui.theme.DarkLightModePreviews
import com.example.statecompose.ui.theme.FontScalePreviews
import com.example.statecompose.ui.theme.MyComposeAppTheme
import com.example.statecompose.ui.theme.boundedClickable
import com.example.statecompose.ui.theme.randomColor

//@Preview(showSystemUi = true)
@Composable
fun LayoutHomeScreenPortrait() {
    Scaffold(
        bottomBar = {
            BottomNavBar { }
        },
        modifier = Modifier.statusBarsPadding()
    ) { innerPadding ->
        LayoutHomeScreenContent(
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                .padding(innerPadding)
        )
    }
}

/*@Preview(
    name = "Phone - Landscape",
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape",
    showSystemUi = true
)*/
@Composable
fun LayoutHomeScreenLandscape() {
    Row {
        LayoutScreenNavigationRail()
        LayoutHomeScreenContent(
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                .padding(12.dp)
        )
    }
}

@Composable
private fun TopSearchBar(modifier: Modifier = Modifier) {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        placeholder = {
            Text("Search")
        },
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}


@Composable
private fun LayoutHomeScreenContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        TopSearchBar()
        FirstRowContent()
        SecondRowContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun FirstRowContent() {
    Text(
        text = "Align your Body",
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .padding(start = 8.dp)
            .paddingFromBaseline(top = 16.dp, bottom = 8.dp)
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = MaterialTheme.colorScheme.surfaceVariant),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(10, key = { it }) {
            TaskCard(
                drawableRes = R.drawable.ic_launcher_foreground,
                drawableColor = randomColor(),
                cardTitle = R.string.app_name
            ) { }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SecondRowContent(modifier: Modifier = Modifier) {
    Text(
        text = "Favorite Collections",
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .padding(start = 8.dp)
            .paddingFromBaseline(top = 16.dp, bottom = 8.dp)
    )
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.height(180.dp)
    ) {
        items(20, key = { it }) {
            CollectionCard(
                drawableRes = R.drawable.ic_launcher_foreground,
                drawableColor = randomColor(),
                cardTitle = R.string.app_name,
                modifier = Modifier.height(80.dp)
            ) { }
        }
    }
}

@Preview
@Composable
private fun BottomNavBar(modifier: Modifier = Modifier, onTabClick: () -> Unit = {}) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceDim
    ) {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Rounded.Person, contentDescription = null) },
            label = { Text("Profile", color = MaterialTheme.colorScheme.onSurface) },
            colors = NavigationBarItemColors(
                selectedIconColor = MaterialTheme.colorScheme.onSurface,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                selectedTextColor = Color.Transparent,
                selectedIndicatorColor = Color.Transparent,
                unselectedTextColor = Color.Transparent,
                disabledIconColor = Color.Transparent,
                disabledTextColor = Color.Transparent
            ),
            selected = true,
            onClick = { onTabClick() }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Rounded.Settings, contentDescription = null) },
            label = { Text("Settings", color = MaterialTheme.colorScheme.onSurface) },
            colors = NavigationBarItemColors(
                selectedIconColor = MaterialTheme.colorScheme.onSurface,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                selectedTextColor = Color.Transparent,
                selectedIndicatorColor = Color.Transparent,
                unselectedTextColor = Color.Transparent,
                disabledIconColor = Color.Transparent,
                disabledTextColor = Color.Transparent
            ),
            selected = true,
            onClick = { onTabClick() }
        )
    }
}

@Composable
private fun LayoutScreenNavigationRail(modifier: Modifier = Modifier, onTabClick: () -> Unit = {}) {
    NavigationRail(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigationRailItem(
                icon = { Icon(imageVector = Icons.Rounded.Person, contentDescription = null) },
                label = { Text("Profile", color = MaterialTheme.colorScheme.onSurface) },
                colors = NavigationRailItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = Color.Transparent,
                    selectedIndicatorColor = Color.Transparent,
                    unselectedTextColor = Color.Transparent,
                    disabledIconColor = Color.Transparent,
                    disabledTextColor = Color.Transparent
                ),
                selected = true,
                onClick = { onTabClick() }
            )
            NavigationRailItem(
                icon = { Icon(imageVector = Icons.Rounded.Settings, contentDescription = null) },
                label = { Text("Settings", color = MaterialTheme.colorScheme.onSurface) },
                colors = NavigationRailItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = Color.Transparent,
                    selectedIndicatorColor = Color.Transparent,
                    unselectedTextColor = Color.Transparent,
                    disabledIconColor = Color.Transparent,
                    disabledTextColor = Color.Transparent
                ),
                selected = true,
                onClick = { onTabClick() }
            )
        }
    }
}

@FontScalePreviews
@Composable
private fun PreviewTaskCard() {
    TaskCard(
        drawableRes = R.drawable.ic_launcher_foreground,
        drawableColor = MaterialTheme.colorScheme.onSurfaceVariant,
        cardTitle = R.string.app_name
    )
}

@Composable
private fun TaskCard(
    @DrawableRes drawableRes: Int,
    drawableColor: Color,
    @StringRes cardTitle: Int,
    modifier: Modifier = Modifier,
    onTaskClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.surfaceVariant,)
            .width(IntrinsicSize.Min) // follows the minimum width of the child composable
            .boundedClickable { onTaskClick() }
            .padding(top = 12.dp, start = 12.dp, end = 12.dp),
    ) {
        Image(
            painter = painterResource(id = drawableRes),
            contentDescription = null,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .background(color = drawableColor),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = stringResource(cardTitle),
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 12.dp),
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@DarkLightModePreviews
@Composable
private fun PreviewCollectionCard() {
    MyComposeAppTheme {
        CollectionCard(
            drawableRes = R.drawable.ic_launcher_foreground,
            drawableColor = MaterialTheme.colorScheme.inverseOnSurface,
            cardTitle = R.string.app_name
        )
    }
}

@Composable
private fun CollectionCard(
    @DrawableRes drawableRes: Int,
    drawableColor: Color,
    @StringRes cardTitle: Int,
    modifier: Modifier = Modifier,
    onCollectionClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.inverseOnSurface)
            .boundedClickable { onCollectionClick() }
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = drawableRes),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = drawableColor,
                    shape = MaterialTheme.shapes.small
                ),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = stringResource(cardTitle),
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}