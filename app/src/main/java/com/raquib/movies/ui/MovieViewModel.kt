package com.raquib.movies.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raquib.movies.MovieRepository
import com.raquib.movies.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieViewModel(application: Application, private val movieRepository: MovieRepository) : AndroidViewModel(application) {

    private val moviesLiveData = MutableLiveData<List<Movie>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            moviesLiveData.postValue(movieRepository.getMovies())
            Timber.d(movieRepository.getMovieDetail(movieRepository.getMovies()[0].name).actors[0].name)
        }
    }

    fun getMovies() = moviesLiveData
    fun getMovieDetail(movieName: String) = movieRepository.getMovieDetail(movieName)

}
