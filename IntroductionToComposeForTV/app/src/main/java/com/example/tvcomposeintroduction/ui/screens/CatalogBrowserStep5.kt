/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.tvcomposeintroduction.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Tab
import androidx.tv.material3.TabRow
import androidx.tv.material3.Text
import com.example.tvcomposeintroduction.data.Movie

@Composable
fun CatalogBrowserStep5(
    modifier: Modifier = Modifier,
    catalogBrowserViewModel: CatalogBrowserViewModel = viewModel(),
    onMovieSelected: (Movie) -> Unit = {}
) {
    val featuredMovieList by catalogBrowserViewModel.featuredMovieList.collectAsState()
    val categoryList by catalogBrowserViewModel.categoryList.collectAsState()
    val selectedTabIndex = remember { mutableIntStateOf(0) }

    TopNavigation(selectedTabIndex = selectedTabIndex)
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun TopNavigation(selectedTabIndex: MutableIntState) {
    val tabs = listOf("Home", "Movies")

    TabRow(
        selectedTabIndex = selectedTabIndex.value,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = (index == selectedTabIndex.value),
                onFocus = { selectedTabIndex.value = index },
//                onClick = {}
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 20.dp,
                        vertical = 8.dp)
                )
            }
        }
    }
}
