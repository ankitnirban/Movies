package com.example.movies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.network.MoviesRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel
@Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    private val _homeScreenState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState.Loading)
    val homeScreenState = _homeScreenState.asStateFlow()


    fun fetchTopRatedMovies() {
        _homeScreenState.value = HomeScreenState.Loading
        try {
            viewModelScope.launch {
                _homeScreenState.value =
                    HomeScreenState.Success(
                        topRatedMovies = moviesRepository.getTopRatedMovies(),
                        topActionMovies = moviesRepository.getTopRatedMovies()
                            .filter { it.genreIds?.contains(28) ?: false },
                        topAnimationMovies = moviesRepository.getTopRatedMovies()
                            .filter { it.genreIds?.contains(16) ?: false }
                    )
            }
        } catch (e: Exception) {
            _homeScreenState.value = HomeScreenState.Error(e.message)
        }
    }
}