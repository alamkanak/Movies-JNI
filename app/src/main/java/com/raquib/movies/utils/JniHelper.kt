package com.raquib.movies.utils

import com.raquib.movies.model.Movie
import com.raquib.movies.model.MovieDetail

class JniHelper {
    external suspend fun getMovies(): Array<Movie>
    external suspend fun getMovieDetail(name: String): MovieDetail
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}