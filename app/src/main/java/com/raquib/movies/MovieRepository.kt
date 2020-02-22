package com.raquib.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raquib.movies.model.Movie

class MovieRepository(private val jniHelper: JniHelper) {

    private val moviesLiveData = MutableLiveData<List<Movie>>()

    fun getMovies(): LiveData<List<Movie>> {
        return moviesLiveData
    }

    fun refreshMovies() {
        val movies = jniHelper.getMovies()
        moviesLiveData.postValue(movies.asList())
    }

    fun getMovieDetail(movieName: String) {

    }
}