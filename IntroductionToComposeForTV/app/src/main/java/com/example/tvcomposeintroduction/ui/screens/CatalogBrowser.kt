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

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import androidx.tv.material3.Button
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.CardLayoutDefaults
import androidx.tv.material3.Carousel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.StandardCardLayout
import androidx.tv.material3.Tab
import androidx.tv.material3.TabRow
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.example.tvcomposeintroduction.R
import com.example.tvcomposeintroduction.data.Movie

@Composable
fun CatalogBrowser(
    modifier: Modifier = Modifier,
    catalogBrowserViewModel: CatalogBrowserViewModel = viewModel(),
    onMovieSelected: (Movie) -> Unit = {}
) {
    val featuredMovieList by catalogBrowserViewModel.featuredMovieList.collectAsState()
    val categoryList by catalogBrowserViewModel.categoryList.collectAsState()

    CategoryBrowserAppContent(featuredMovieList, categoryList)
}

@Composable
private fun CategoryBrowserAppContent(featuredMovies: List<Movie>, categoryList: List<Category>) {
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    Column {
        TopNavigation(selectedTabIndex = selectedTabIndex)
        AnimatedContent(
            targetState = selectedTabIndex.value, label = "",
            transitionSpec = { fadeIn().togetherWith(fadeOut()) }
        ) { index ->
            when(index) {
                0 -> HomeScreenContent(featuredMovies)
                1 -> MovieCategoriesContent(categoryList)
            }
        }
    }
}

@Composable
private fun HomeScreenContent(featuredMovies: List<Movie>) {
    TvLazyColumn {
        item {
            FeaturedCarouselContent(featuredMovies)
        }
        item {
            MoviesRowContent("Trending", featuredMovies)
        }
    }
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
                onFocus = { selectedTabIndex.value = index }
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical = 8.dp
                    )
                )
            }
        }
    }
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
                    verticalArrangement = Arrangement.spacedBy(10.dp)
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

@Composable
private fun MovieCategoriesContent(
    categoryList: List<Category>
) {
    TvLazyColumn {
        items(categoryList) {
            MoviesRowContent(
                categoryTitle = it.name,
                movies = it.movieList
            )
        }
    }
}

@Composable
private fun MoviesRowContent(
    categoryTitle: String,
    movies: List<Movie>
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = categoryTitle,
            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
            style = TextStyle(fontSize = 24.sp)
        )
        TvLazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(30.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(movies) {
                MovieCard(movie = it)
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    onClick: (Movie) -> Unit = {}
) {
    StandardCardLayout(
        modifier = modifier,
        imageCard = {
            CardLayoutDefaults.ImageCard(
                onClick = { onClick(movie) },
                interactionSource = it
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(300.dp)
                        .aspectRatio(CardDefaults.HorizontalImageAspectRatio),
                    model = movie.cardImageUrl,
                    contentDescription = movie.description,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(
                        id = R.drawable.placeholder
                    )
                )
            }
        },
        title = {
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = movie.title
            )
        })
}