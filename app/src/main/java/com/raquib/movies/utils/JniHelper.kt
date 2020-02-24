package com.raquib.movies.utils

import com.raquib.movies.model.Movie
import com.raquib.movies.model.MovieDetail
import timber.log.Timber

class JniHelper {
    external suspend fun getMovies(): Array<Movie>
    external suspend fun getMovieDetail(name: String): MovieDetail

    companion object {
        init {
            Timber.d("Init native lib")
            System.loadLibrary("native-lib")
        }
    }
}