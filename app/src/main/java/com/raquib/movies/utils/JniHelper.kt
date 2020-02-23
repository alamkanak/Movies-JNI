package com.raquib.movies.utils

import com.raquib.movies.model.Movie
import com.raquib.movies.model.MovieDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class JniHelper {
    external suspend fun getMovies(): Array<Movie>
    external suspend fun getMovieDetail(name: String): MovieDetail

    init {
        GlobalScope.launch(Dispatchers.IO) {
            System.loadLibrary("native-lib")
        }
    }

}