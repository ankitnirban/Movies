package com.example.movies.ui

import com.example.movies.domain.Movie

sealed interface HomeScreenState {
    data object Loading : HomeScreenState
    data class Success(val movies: List<Movie>) : HomeScreenState
    data class Error(
        val error: String? = null
    ) : HomeScreenState
}