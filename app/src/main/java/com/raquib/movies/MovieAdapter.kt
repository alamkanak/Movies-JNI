package com.raquib.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raquib.movies.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var clickListener: MovieClickListener? = null
    private var movies: List<Movie> = listOf()

    inner class ViewHolder(private val view: ViewGroup) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            view.textViewTitle.text = movie.name
            view.textViewDescription.text = view.context.getString(R.string.last_updated, movie.lastUpdated)
            view.setOnClickListener {
                clickListener?.onMovieClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false) as ViewGroup
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
    }

    fun setMovieClickListener(listener: MovieClickListener) {
        this.clickListener = listener
    }
}