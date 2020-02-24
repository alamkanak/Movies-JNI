package com.raquib.movies.ui

import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.view_toolbar.*

/**
 * The parent class of all fragments allowing them to customize the toolbar.
 */
abstract class BaseFragment : Fragment() {

    fun setupToolbar(title: String? = null, hasBackButton: Boolean = false) {
        activity?.let {activity ->
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            val actionBar = activity.supportActionBar
            if (!TextUtils.isEmpty(title)) {
                actionBar?.title = title
            }
            actionBar?.setHomeButtonEnabled(hasBackButton)
            actionBar?.setDisplayHomeAsUpEnabled(hasBackButton)
        }
    }

}
