package com.raquib.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raquib.movies.model.Movie

class MovieRepository(private val jniHelper: JniHelper) {
    fun getMovies() = jniHelper.getMovies().asList()
    fun getMovieDetail(movieName: String) = jniHelper.getMovieDetail(movieName)
}