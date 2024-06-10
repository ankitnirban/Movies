package com.example.movies.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.movies.ui.presentation.HomeScreenScaffold

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
                HomeScreenScaffold(viewModel = viewModel)
            }
        }
    }
}