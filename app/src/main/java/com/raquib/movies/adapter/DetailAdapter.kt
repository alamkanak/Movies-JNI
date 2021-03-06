package com.raquib.movies.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatDrawableManager
import androidx.recyclerview.widget.RecyclerView
import com.raquib.movies.R
import com.raquib.movies.model.Actor
import com.raquib.movies.model.MovieDetail
import com.raquib.movies.utils.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_actor.view.*
import kotlinx.android.synthetic.main.item_detail_header.view.*
import kotlinx.android.synthetic.main.item_movie.view.textViewDescription
import kotlinx.android.synthetic.main.item_movie.view.textViewTitle

/**
 * Responsible for laying out movie details in a recycler view. First, it renders movie name,
 * description etc. as an item. And then it renders casts in their own rows separately. So, this
 * adapter has two item types: detail header and casts.
 */
class DetailAdapter(private val picasso: Picasso) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movieTitle: String? = null
    private var movieDescription: String? = null
    private var movieScore: Float? = null
    private var actors: List<Actor> = listOf()

    companion object {
        const val TYPE_DETAIL = 1
        const val TYPE_ACTOR = 2
    }

    inner class ActorViewHolder(private val view: ViewGroup) : RecyclerView.ViewHolder(view) {
        fun bind(actor: Actor) {

            // Use this method for vector backward compatibility.
            val placeholder = AppCompatDrawableManager.get().getDrawable(view.context, R.drawable.ic_actor_placeholder)

            // Display avatar or placeholder image.
            if (!TextUtils.isEmpty(actor.imageUrl)) {
                picasso.load(actor.imageUrl)
                    .transform(CircleTransform())
                    .placeholder(placeholder)
                    .into(view.imageViewAvatar)
            }
            else {
                view.imageViewAvatar.setImageResource(R.drawable.ic_actor_placeholder)
            }

            // Display texts.
            view.textViewName.text = actor.name
            view.textViewAge.text = view.context.getString(R.string.age, actor.age)
        }
    }

    inner class DetailViewHolder(private val view: ViewGroup) : RecyclerView.ViewHolder(view) {
        fun bind(movieTitle: String, movieDescription: String, movieScore: Float) {
            view.textViewTitle.text = movieTitle
            view.ratingBar.rating = 5*movieScore/10
            view.textViewRating.text = view.context.getString(R.string.rating, movieScore.toInt())
            view.textViewDescription.text = movieDescription
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_DETAIL) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_header, parent, false) as ViewGroup
            DetailViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_actor, parent, false) as ViewGroup
            ActorViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_DETAIL -> if (movieTitle != null && movieDescription != null && movieScore != null) {
                (holder as DetailViewHolder).bind(movieTitle!!, movieDescription!!, movieScore!!)
            }
            TYPE_ACTOR -> (holder as ActorViewHolder).bind(actors[position-1])
        }
    }

    override fun getItemCount(): Int {
        return actors.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_DETAIL
            else -> TYPE_ACTOR
        }
    }

    fun setMovieDetail(movieDetail: MovieDetail) {
        movieTitle = movieDetail.name
        movieDescription = movieDetail.description
        movieScore = movieDetail.score
        this.actors = movieDetail.actors.asList()
        notifyDataSetChanged()
    }
}