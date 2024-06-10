package com.example.movies.network

import com.example.movies.domain.Movie
import com.example.movies.utils.Mapper
import javax.inject.Inject

class MoviesRepository
@Inject constructor(
    private val moviesService: MoviesService
) {
    suspend fun getTopRatedMovies(): List<Movie> {
        val networkResponse = moviesService.getTopRatedMovies()
        val movies = Mapper.mapMovieListDTOToMovieList(networkResponse?.results.orEmpty())
        return movies
    }
}