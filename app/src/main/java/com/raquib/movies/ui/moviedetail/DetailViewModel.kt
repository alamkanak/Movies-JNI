package com.raquib.movies.ui.moviedetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.raquib.movies.repo.MovieRepository

class DetailViewModel(application: Application, private val movieRepository: MovieRepository) : AndroidViewModel(application) {
    fun getMovieDetail(movieName: String) = movieRepository.getMovieDetail(movieName)
}
