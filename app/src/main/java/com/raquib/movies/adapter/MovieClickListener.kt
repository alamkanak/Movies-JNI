package com.raquib.movies.adapter

import com.raquib.movies.model.Movie

interface MovieClickListener {
    fun onMovieClick(movie: Movie, position: Int)
}