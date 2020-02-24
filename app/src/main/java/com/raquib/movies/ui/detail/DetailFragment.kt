package com.raquib.movies.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.raquib.movies.R
import com.raquib.movies.adapter.DetailAdapter
import com.raquib.movies.model.Resource
import com.raquib.movies.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.view_shimmer.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Fragment that displays details about a movie in the UI.
 */
class DetailFragment : BaseFragment() {

    private val viewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()
    private val adapter: DetailAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Setup recycler view.
        val isTablet = resources.getBoolean(R.bool.is_tablet)
        activity?.let {
            val viewManager = LinearLayoutManager(it)
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = this@DetailFragment.adapter
            }
        }

        // Setup toolbar.
        setupToolbar(getString(R.string.app_name), true)

        // Display movie detail and loading screen.
        viewModel.getMovieDetail(args.movieName).observe(viewLifecycleOwner, Observer { resource ->
            when (resource.getStatus()) {
                Resource.Status.SUCCESS -> {

                    // Hide loading animation.
                    shimmerFrameLayout.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE

                    // Display detail in the UI.
                    resource.data?.let { movie ->
                        adapter.setMovieDetail(movie)
                        if (!isTablet) {
                            activity?.let {
                                // Change toolbar title.
                                setupToolbar(movie.name, true)
                            }
                        }
                    }
                }
                Resource.Status.LOADING -> {
                    // Show loading animation.
                    shimmerFrameLayout.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().navigateUp()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
