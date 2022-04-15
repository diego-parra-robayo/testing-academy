package com.wizeline.academy.testing.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wizeline.academy.testing.domain.Movie
import com.wizeline.academy.testing.domain.MoviesRepository
import com.wizeline.academy.testing.utils.Event
import com.wizeline.academy.testing.utils.Resource
import com.wizeline.academy.testing.utils.toResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _movies = MutableLiveData<Resource<List<Movie>>>(Resource.Loading)
    val movies: LiveData<Resource<List<Movie>>> = _movies

    init {
        viewModelScope.launch {
            _movies.value = moviesRepository.getMovies().toResource()
        }
    }

    private val _navigateDetails = MutableLiveData<Event<String>>()
    val navigateDetails: LiveData<Event<String>> = _navigateDetails

    fun onMovieClick(movieId: String) {
        _navigateDetails.value = Event(movieId)
    }

}