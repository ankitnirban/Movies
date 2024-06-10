package com.example.movies.ui.presentation

import com.example.movies.domain.Movie

sealed interface HomeScreenState {
    data object Loading : HomeScreenState
    data class Success(
        val topRatedMovies: List<Movie>,
        val topActionMovies: List<Movie>,
        val topAnimationMovies: List<Movie>
    ) : HomeScreenState
    data class Error(
        val error: String? = null
    ) : HomeScreenState
}