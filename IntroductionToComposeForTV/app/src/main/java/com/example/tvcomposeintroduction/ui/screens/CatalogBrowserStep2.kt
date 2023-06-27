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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.CardLayoutDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.StandardCardLayout
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.example.tvcomposeintroduction.R
import com.example.tvcomposeintroduction.data.Movie

@Composable
fun CatalogBrowserStep2(
    modifier: Modifier = Modifier,
    catalogBrowserViewModel: CatalogBrowserViewModel = viewModel(),
    onMovieSelected: (Movie) -> Unit = {}
) {
    val featuredMovieList by catalogBrowserViewModel.featuredMovieList.collectAsState()
    val categoryList by catalogBrowserViewModel.categoryList.collectAsState()

    MoviesRowContent("Featured", featuredMovieList)
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