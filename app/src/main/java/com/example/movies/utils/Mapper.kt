package com.example.movies.utils

import com.example.movies.domain.Movie
import com.example.movies.network.model.MovieDTO


object Mapper {
    private fun mapMovieDTOToMovie(movieDTO: MovieDTO): Movie {
        return Movie(
            movieDTO.adult,
            movieDTO.backdropPath,
            movieDTO.genreIds,
            movieDTO.id,
            movieDTO.originalLanguage,
            movieDTO.originalTitle,
            movieDTO.overview,
            movieDTO.popularity,
            movieDTO.posterPath,
            movieDTO.releaseDate,
            movieDTO.title,
            movieDTO.video,
            movieDTO.voteAverage,
            movieDTO.voteCount
        )
    }
    fun mapMovieListDTOToMovieList(movieListDTO: List<MovieDTO>): List<Movie> {
        return movieListDTO.map { mapMovieDTOToMovie(it) }
    }
}