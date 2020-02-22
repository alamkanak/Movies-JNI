package com.raquib.movies

import com.raquib.movies.model.Movie

class JniHelper {
    external fun getMovies(): Array<Movie>
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}