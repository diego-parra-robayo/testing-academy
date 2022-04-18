package com.wizeline.academy.testing.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.wizeline.academy.testing.domain.Movie
import com.wizeline.academy.testing.domain.MoviesRepository
import com.wizeline.academy.testing.utils.Event
import com.wizeline.academy.testing.utils.Resource
import com.wizeline.academy.testing.utils.toResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    val filterOptions = listOf(
        FilterOption.ALL,
        FilterOption.FAVORITES
    )

    private val _filter = MutableStateFlow(FilterOption.ALL)
    val filter: StateFlow<FilterOption> = _filter
    fun selectFilter(filterOption: FilterOption) {
        _filter.value = filterOption
    }

    val movies = _filter
        .map {
            when (it) {
                FilterOption.ALL -> getAllMovies()
                FilterOption.FAVORITES -> getFavoriteMovies()
            }
        }
        .asLiveData()

    private suspend fun getAllMovies(): Resource<List<Movie>> =
        moviesRepository.getMovies().toResource()

    private suspend fun getFavoriteMovies(): Resource<List<Movie>> =
        moviesRepository.getFavorites()
            .map { it.mapNotNull { moviesRepository.getMovie(it).getOrNull() } }
            .toResource()


    private val _navigateDetails = MutableLiveData<Event<String>>()
    val navigateDetails: LiveData<Event<String>> = _navigateDetails

    fun onMovieClick(movieId: String) {
        _navigateDetails.value = Event(movieId)
    }

}

