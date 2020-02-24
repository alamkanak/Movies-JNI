package com.raquib.movies.repo

import com.raquib.movies.utils.JniHelper

/**
 * Data repository responsible for serving information about movies from JNI.
 */
class MovieRepository(private val jniHelper: JniHelper) {
    suspend fun getMovies() = jniHelper.getMovies().asList()
    suspend fun getMovieDetail(movieName: String) = jniHelper.getMovieDetail(movieName)
}