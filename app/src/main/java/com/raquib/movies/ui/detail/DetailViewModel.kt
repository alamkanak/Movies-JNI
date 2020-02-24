package com.raquib.movies.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raquib.movies.model.MovieDetail
import com.raquib.movies.model.Resource
import com.raquib.movies.repo.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application, private val movieRepository: MovieRepository) : AndroidViewModel(application) {

    private val movieDetailLiveData = MutableLiveData<Resource<MovieDetail>>()

    fun getMovieDetail(movieName: String): LiveData<Resource<MovieDetail>> {
        movieDetailLiveData.postValue(Resource.loading())
        viewModelScope.launch(Dispatchers.IO) {
            movieDetailLiveData.postValue(Resource.success(movieRepository.getMovieDetail(movieName)))
        }
        return movieDetailLiveData
    }
}
