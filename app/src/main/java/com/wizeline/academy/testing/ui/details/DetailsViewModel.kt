package com.wizeline.academy.testing.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.wizeline.academy.testing.domain.MovieDetails
import com.wizeline.academy.testing.domain.MoviesRepository
import com.wizeline.academy.testing.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId = savedStateHandle.get<String>(MOVIE_ID_SAVED_STATE_KEY) ?: ""

    private val _movieDetails = MutableLiveData<Resource<MovieDetails>>(Resource.Loading)
    val movieDetails: LiveData<Resource<MovieDetails>> = _movieDetails

    init {
        moviesRepository.getMovieDetails(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _movieDetails.value = Resource.Success(it)
            }, {
                _movieDetails.value = Resource.Failure(it)
            })
    }

    val isFavorite = moviesRepository
        .isFavoriteAsFlow(movieId)
        .distinctUntilChanged()
        .asLiveData()

    fun toggleFavorite() = viewModelScope.launch {
        val isFavorite = moviesRepository.isFavorite(movieId)
        if (isFavorite) {
            moviesRepository.removeFavorite(movieId)
        } else {
            moviesRepository.addFavorite(movieId)
        }
    }

    companion object {
        const val MOVIE_ID_SAVED_STATE_KEY = "movie_id"
    }

}