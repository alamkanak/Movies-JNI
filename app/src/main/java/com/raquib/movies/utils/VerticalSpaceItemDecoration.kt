package com.raquib.movies.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * A recycler view divider.
 */
class VerticalSpaceItemDecoration(private val context: Context, private val height: Int) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = context.resources.getDimensionPixelOffset(height)
    }
}