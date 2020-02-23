package com.raquib.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raquib.movies.R
import com.raquib.movies.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var clickListener: MovieClickListener? = null
    private var movies: List<Movie> = listOf()
    private var focusedItem = -1

    inner class ViewHolder(private val view: ViewGroup) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            view.textViewTitle.text = movie.name
            view.textViewDescription.text = view.context.getString(R.string.last_updated, movie.lastUpdated)

            if (adapterPosition == focusedItem) {
                view.imageViewIndicator.visibility = View.VISIBLE
            }
            else {
                view.imageViewIndicator.visibility = View.GONE
            }

            view.setOnClickListener {
                if (view.resources.getBoolean(R.bool.is_tablet)) {
                    notifyItemChanged(focusedItem)
                    focusedItem = adapterPosition
                    notifyItemChanged(focusedItem)
                }
                clickListener?.onMovieClick(movie, adapterPosition)
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
        notifyDataSetChanged()
    }

    fun setMovieClickListener(listener: MovieClickListener) {
        this.clickListener = listener
    }

    fun setSelectedPosition(selectedPosition: Int) {
        focusedItem = selectedPosition
        notifyItemChanged(focusedItem)
    }
}