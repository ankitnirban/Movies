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
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movies.domain.Movie
import com.example.movies.network.MoviesRepository
import com.example.movies.network.MoviesService

import com.example.movies.network.model.MoviesResponseDTO
import com.example.movies.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchTopRatedMovies()
        enableEdgeToEdge()
        setContent {
            MoviesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { it ->
                    val innerPadding = it
                    val homeScreenState = viewModel.homeScreenState.collectAsState()
                    when (val state = homeScreenState.value) {
                        is HomeScreenState.Loading -> {
                            Text("Loading...", modifier = Modifier.padding(innerPadding))
                        }

                        is HomeScreenState.Success -> {
                            HomeScreen(
                                topRatedMovies = state.movies,
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
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Top Rated Movies"
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(topRatedMovies.size) { index ->
                MovieItem(movie = topRatedMovies[index])
            }
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
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
