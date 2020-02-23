package com.raquib.movies

import com.raquib.movies.model.Movie

interface MovieClickListener {
    fun onMovieClick(movie: Movie)
}