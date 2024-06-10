package com.example.movies.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movies.domain.Movie
import androidx.compose.material3.Text
import com.example.movies.ui.MoviesViewModel

@Composable
fun HomeScreenScaffold(
    viewModel: MoviesViewModel,
    modifier: Modifier = Modifier,
    onSeeAllClick: (String) -> Unit = {}
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val homeScreenState = viewModel.homeScreenState.collectAsState()
        when (val state = homeScreenState.value) {
            is HomeScreenState.Loading -> {
                Text("Loading...", modifier = Modifier.padding(innerPadding))
            }

            is HomeScreenState.Success -> {
                HomeScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding),
                    onSeeAllClick = onSeeAllClick
                )
            }

            is HomeScreenState.Error -> {
                Text("Error: ${state.error}", modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
fun HomeScreen(
    viewModel: MoviesViewModel,
    modifier: Modifier = Modifier,
    onSeeAllClick: (String) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val homeScreenState by viewModel.homeScreenState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MoviesSection((homeScreenState as HomeScreenState.Success).topRatedMovies, "Top Rated", onSeeAllClick = onSeeAllClick)
        Spacer(modifier = Modifier.height(16.dp))
        MoviesSection((homeScreenState as HomeScreenState.Success).topActionMovies, "Action", onSeeAllClick = onSeeAllClick)
        Spacer(modifier = Modifier.height(16.dp))
        MoviesSection((homeScreenState as HomeScreenState.Success).topAnimationMovies, "Animation", onSeeAllClick = onSeeAllClick)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MoviesSection(
    movies: List<Movie> = emptyList(),
    title: String = "Movies",
    modifier: Modifier = Modifier,
    onSeeAllClick: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = AnnotatedString(title)
        )
        Text(
            text = AnnotatedString("See all"),
            color = Blue,
            modifier = Modifier.clickable { onSeeAllClick(title) }
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies.size) { index ->
            MovieItem(movie = movies[index])
        }
    }
}

@Composable
fun MovieItem(movie: Movie, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .width(200.dp)
            .background(Color.Gray)
    ) {
        AsyncImage(
            model = movie.posterPath.orEmpty(),
            contentDescription = "Image from URL",
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = AnnotatedString(movie.title.orEmpty()),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
