package com.example.movies.network

import com.example.movies.network.model.MoviesResponseDTO
import com.example.movies.utils.AUTH_TOKEN
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MoviesService {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Header("Authorization") authorization: String = AUTH_TOKEN,
        @Query("language") language: String = "en-US"
    ): MoviesResponseDTO
}