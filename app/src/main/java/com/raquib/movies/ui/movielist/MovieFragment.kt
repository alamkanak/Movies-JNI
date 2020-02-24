package com.raquib.movies.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.raquib.movies.R
import com.raquib.movies.adapter.MovieAdapter
import com.raquib.movies.adapter.MovieClickListener
import com.raquib.movies.model.Movie
import com.raquib.movies.model.Resource
import com.raquib.movies.ui.BaseFragment
import com.raquib.movies.utils.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.view_shimmer.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : BaseFragment() {

    private var selectedPosition: Int = -1
    private val viewModel: MovieViewModel by viewModel()
    private val adapter: MovieAdapter by inject()
    private val KEY_SELECTED_POSITION = "selectedPosition"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val isTablet = resources.getBoolean(R.bool.is_tablet)

        // Setup recycler view.
        activity?.let {
            val viewManager = LinearLayoutManager(it)
            val dividerItemDecoration = VerticalSpaceItemDecoration(recyclerView.context, R.dimen.divider)
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = this@MovieFragment.adapter
                addItemDecoration(dividerItemDecoration)
            }
            (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        // Open detail view when a movie is clicked.
        adapter.setMovieClickListener(object:
            MovieClickListener {
            override fun onMovieClick(movie: Movie, position: Int) {
                selectedPosition = position
                if (isTablet) {
                    val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_tablet) as NavHostFragment
                    navHostFragment.navController.navigate(R.id.detailFragment, bundleOf("movieName" to movie.name))
                }
                else {
                    findNavController().navigate(MovieFragmentDirections.actionMovieFragmentToDetailFragment(movie.name))
                }
            }
        })

        activity?.let {
            setupToolbar()
        }

        // Observe movies from data source. And show them in the list.
        viewModel.getMovies().observe(viewLifecycleOwner, Observer {
            when (it.getStatus()) {
                Resource.Status.SUCCESS -> {
                    shimmerFrameLayout.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    it.data?.let {movies ->
                        adapter.setMovies(movies)
                    }
                }
                else -> {
                    shimmerFrameLayout.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
        })

        // Restore selected position in the list.
        if (isTablet) {
            selectedPosition = savedInstanceState?.getInt(KEY_SELECTED_POSITION, selectedPosition) ?: selectedPosition
            if (selectedPosition >= 0) {
                adapter.setSelectedPosition(selectedPosition)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_POSITION, selectedPosition)
    }
}
