package com.raquib.movies.ui.movielist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raquib.movies.model.Movie
import com.raquib.movies.model.Resource
import com.raquib.movies.repo.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MovieViewModel(application: Application, private val movieRepository: MovieRepository) : AndroidViewModel(application) {

    private val moviesLiveData = MutableLiveData<Resource<List<Movie>>>()

    init {
        moviesLiveData.postValue(Resource.loading())
        viewModelScope.launch(Dispatchers.IO) {
            delay(5000)
            moviesLiveData.postValue(Resource.success(movieRepository.getMovies()))
        }
    }

    fun getMovies() = moviesLiveData

}
