package com.raquib.movies.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.raquib.movies.R
import kotlinx.android.synthetic.main.movie_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    val viewModel: MovieViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getMovies().observe(viewLifecycleOwner, Observer {
            textView.text = it.size.toString()
        })
    }
}
