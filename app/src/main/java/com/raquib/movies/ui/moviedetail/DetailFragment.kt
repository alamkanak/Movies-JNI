package com.raquib.movies.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.raquib.movies.R
import com.raquib.movies.adapter.DetailAdapter
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()
    private val adapter: DetailAdapter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Setup recycler view.
        activity?.let {
            val viewManager = LinearLayoutManager(it)
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = this@DetailFragment.adapter
            }
        }

        // Display movie detail.
        viewModel.getMovieDetail(args.movieName).observe(viewLifecycleOwner, Observer {
            adapter.setMovieDetail(it)
        })
    }
}
