package com.raquib.movies.utils

import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity

fun setupToolbar(activity: FragmentActivity, toolbar: Toolbar, title: String? = null, hasBackButton: Boolean = false) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    val actionBar = activity.supportActionBar
    if (!TextUtils.isEmpty(title)) {
        actionBar?.title = title
    }
    actionBar?.setHomeButtonEnabled(hasBackButton)
    actionBar?.setDisplayHomeAsUpEnabled(hasBackButton)
}