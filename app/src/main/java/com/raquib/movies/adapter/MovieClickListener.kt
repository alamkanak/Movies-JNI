package com.raquib.movies.adapter

import com.raquib.movies.model.Movie

/**
 * Interface for handling click event in the movie list.
 */
interface MovieClickListener {
    fun onMovieClick(movie: Movie, position: Int)
}