package com.raquib.movies.repo

import com.raquib.movies.utils.JniHelper

class MovieRepository(private val jniHelper: JniHelper) {
    fun getMovies() = jniHelper.getMovies().asList()
    fun getMovieDetail(movieName: String) = jniHelper.getMovieDetail(movieName)
}