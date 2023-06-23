package com.example.tvcomposeintroduction.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.tv.material3.DrawerValue
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.ListItem
import androidx.tv.material3.ListItemDefaults
import androidx.tv.material3.ListItemScale
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.ModalNavigationDrawer
import androidx.tv.material3.Text
import com.example.tvcomposeintroduction.ui.screens.CatalogBrowser
import com.example.tvcomposeintroduction.ui.screens.CatalogBrowserFocusRestorer
import com.example.tvcomposeintroduction.ui.screens.CatalogBrowserStep0
import com.example.tvcomposeintroduction.ui.screens.CatalogBrowserStep1
import com.example.tvcomposeintroduction.ui.screens.CatalogBrowserStep2
import com.example.tvcomposeintroduction.ui.screens.CatalogBrowserStep3
import com.example.tvcomposeintroduction.ui.screens.CatalogBrowserStep4
import com.example.tvcomposeintroduction.ui.screens.CatalogBrowserStep5

private val navigationItemLabels = listOf(
    "Empty Activity",
    "Cards",
    "Lazy Row",
    "Lazy Column",
    "Featured Carousel",
    "Top Navigation",
    "Category Browser",
    "Focus restoration"
)

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SideNavigationDrawer() {
    val selectedIndex = remember { mutableIntStateOf(0) }
    ModalNavigationDrawer(
        drawerContent = {
            Sidebar(selectedIndex = selectedIndex, drawerValue = it)
        }
    ) {
        Box(Modifier.padding(start = 4.dp, end = 16.dp)) {
            when (selectedIndex.value) {
                0 -> CatalogBrowserStep0()
                1 -> CatalogBrowserStep1()
                2 -> CatalogBrowserStep2()
                3 -> CatalogBrowserStep3()
                4 -> CatalogBrowserStep4()
                5 -> CatalogBrowserStep5()
                6 -> CatalogBrowser()
                7 -> CatalogBrowserFocusRestorer()
            }
        }
    }
}

@OptIn(
    ExperimentalTvMaterial3Api::class,
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
private fun Sidebar(
    selectedIndex: MutableIntState,
    drawerValue: DrawerValue
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .focusRestorer()
            .focusGroup(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
    ) {
        navigationItemLabels.forEachIndexed { index, itemLabel ->
            NavigationDrawerItem(
                imageVector = Icons.Default.KeyboardArrowRight,
                text = itemLabel,
                drawerValue = drawerValue,
                selectedIndex = selectedIndex,
                index = index
            )
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun NavigationDrawerItem(
    imageVector: ImageVector,
    text: String,
    drawerValue: DrawerValue,
    selectedIndex: MutableState<Int>,
    index: Int,
    modifier: Modifier = Modifier,
) {
    key(index) {
        ListItem(
            modifier = modifier
                .padding(horizontal = 6.dp)
                .width(if (drawerValue == DrawerValue.Closed) 56.dp else 240.dp)
                .height(48.dp),
            selected = selectedIndex.value == index,
            onClick = { selectedIndex.value = index },
            headlineContent = {
                AnimatedVisibility(
                    visible = drawerValue == DrawerValue.Open,
                    enter = fadeIn(animationSpec = tween(500))
                            + slideIn(animationSpec = tween(500)) { IntOffset(200, 0) },
                    exit = fadeOut(animationSpec = tween(500))
                            + slideOut(animationSpec = tween(500)) { IntOffset(200, 0) }
                ) {
                    Text(
                        text = text
                    )
                }
            },
            leadingContent = {
                Icon(
                    imageVector = imageVector,
                    modifier = Modifier.size(ListItemDefaults.IconSize),
                    contentDescription = null,
                )
            },
            shape = ListItemDefaults.shape(shape = RoundedCornerShape(48.dp)),
            scale = ListItemScale.None
        )
    }
}