package com.example.movies.ui.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movies.domain.Movie
import com.example.movies.ui.MoviesViewModel

@Composable
fun GenreMoviesScreen(
    genre: String? = "Top Rated",
    viewModel: MoviesViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val homeScreenState by viewModel.homeScreenState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = when (genre) {
                "Top Rated" -> "Top Rated Movies"
                "Action" -> "Action Movies"
                "Animation" -> "Animation Movies"
                else -> "Movies"
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        MoviesGrid(
            movies = when (genre) {
                "Top Rated" -> (homeScreenState as HomeScreenState.Success).topRatedMovies
                "Action" -> (homeScreenState as HomeScreenState.Success).topActionMovies
                "Animation" -> (homeScreenState as HomeScreenState.Success).topAnimationMovies
                else -> emptyList()
            }
        )
    }
}

@Composable
fun MoviesGrid(movies: List<Movie> = emptyList(), modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(movies.size) { index ->
            MovieItem(movie = movies[index])
        }
    }

}
