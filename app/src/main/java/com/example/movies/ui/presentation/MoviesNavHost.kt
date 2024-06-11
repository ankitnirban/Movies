package com.example.movies.ui.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movies.ui.MoviesViewModel
import com.example.movies.utils.Route

@Composable
fun MoviesNavHost(viewModel: MoviesViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.HOME.name) {
        composable(Route.HOME.name) {
            HomeScreenScaffold(viewModel = viewModel, onSeeAllClick = { genre ->
                navController.navigate("${Route.GENRE.name}/${genre}")
            })
        }
        composable(Route.GENRE.name + "/{genre}", arguments = listOf(navArgument("genre") {
            type = NavType.StringType
        })) { backStackEntry ->
            GenreMoviesScreen(
                genre = backStackEntry.arguments?.getString("genre"), viewModel = viewModel
            )
        }
    }
}