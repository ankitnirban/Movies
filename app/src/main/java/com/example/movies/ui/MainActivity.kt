package com.example.movies.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movies.domain.Movie

import com.example.movies.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchTopRatedMovies()
        enableEdgeToEdge()
        setContent {
            MoviesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val homeScreenState = viewModel.homeScreenState.collectAsState()
                    when (val state = homeScreenState.value) {
                        is HomeScreenState.Loading -> {
                            Text("Loading...", modifier = Modifier.padding(innerPadding))
                        }

                        is HomeScreenState.Success -> {
                            HomeScreen(
                                topRatedMovies = state.topRatedMovies,
                                topActionMovies = state.topActionMovies,
                                topAnimationMovies = state.topAnimationMovies,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        is HomeScreenState.Error -> {
                            Text("Error: ${state.error}", modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    topRatedMovies: List<Movie>,
    topActionMovies: List<Movie>,
    topAnimationMovies: List<Movie>,
    modifier: Modifier = Modifier
) {
    val scrollState =
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MoviesSection(topRatedMovies, "Top Rated Movies")
        Spacer(modifier = Modifier.height(16.dp))
        MoviesSection(topActionMovies, "Action Movies")
        Spacer(modifier = Modifier.height(16.dp))
        MoviesSection(topAnimationMovies, "Animation Movies")
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MoviesSection(
    movies: List<Movie> = emptyList(),
    title: String = "Movies",
    modifier: Modifier = Modifier
) {
    Text(
        text = title
    )
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
            text = movie.title.orEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
