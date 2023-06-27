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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.Button
import androidx.tv.material3.Carousel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.example.tvcomposeintroduction.R
import com.example.tvcomposeintroduction.data.Movie

@Composable
fun CatalogBrowserStep4(
    modifier: Modifier = Modifier,
    catalogBrowserViewModel: CatalogBrowserViewModel = viewModel(),
    onMovieSelected: (Movie) -> Unit = {}
) {
    val featuredMovieList by catalogBrowserViewModel.featuredMovieList.collectAsState()
    val categoryList by catalogBrowserViewModel.categoryList.collectAsState()

    FeaturedCarouselContent(featuredMovieList)
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun FeaturedCarouselContent(
    featuredMovies: List<Movie>
) {
    Carousel(
        itemCount = featuredMovies.size,
        modifier = Modifier
            .fillMaxWidth()
            .height(370.dp),
//        autoScrollDurationMillis = 4000L,
//        contentTransformStartToEnd = fadeIn().togetherWith(fadeOut()),
//        contentTransformEndToStart = fadeIn().togetherWith(fadeOut())
    ) { index ->
        val featuredMovie = featuredMovies[index]
        Box {
            AsyncImage(
                model = featuredMovie.backgroundImageUrl,
                contentDescription = null,
                placeholder = painterResource(
                    id = R.drawable.placeholder
                ),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxSize(),
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.6f),
                                Color.Transparent
                            ),
                            start = Offset(
                                0f,
                                Float.POSITIVE_INFINITY
                            ),
                            end = Offset(
                                Float.POSITIVE_INFINITY,
                                0f
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(40.dp),
                    verticalArrangement = Arrangement
                        .spacedBy(10.dp)
                ) {
                    Text(
                        text = featuredMovie.title,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                    Button(
                        onClick = { /*TODO*/ },
                    ) {
                        Text(text = "Watch now")
                    }
                }
            }
        }
    }
}
