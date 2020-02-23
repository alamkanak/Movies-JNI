package com.raquib.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.raquib.movies.MovieAdapter
import com.raquib.movies.MovieClickListener
import com.raquib.movies.R
import com.raquib.movies.VerticalSpaceItemDecoration
import com.raquib.movies.model.Movie
import kotlinx.android.synthetic.main.movie_fragment.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModel()
    private val viewAdapter: MovieAdapter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Setup recycler view.
        activity?.let {
            val viewManager = LinearLayoutManager(it)
            val dividerItemDecoration = VerticalSpaceItemDecoration(recyclerView.context, R.dimen.divider)
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
                addItemDecoration(dividerItemDecoration)
            }
        }

        // Open detail view when a movie is clicked.
        viewAdapter.setMovieClickListener(object:MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                findNavController().navigate(MovieFragmentDirections.actionMovieFragmentToDetailFragment(movie.name))
            }
        })

        // Observe movies from data source. And show them in the list.
        viewModel.getMovies().observe(viewLifecycleOwner, Observer {
            viewAdapter.setMovies(it)
        })
    }
}
