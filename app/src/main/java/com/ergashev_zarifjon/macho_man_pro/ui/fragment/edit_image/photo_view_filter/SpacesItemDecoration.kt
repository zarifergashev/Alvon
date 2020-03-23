package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.photo_view_filter

import android.graphics.Rect
import android.view.View

import androidx.recyclerview.widget.RecyclerView


class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
            outRect.left = space
            outRect.right = 0
        } else {
            outRect.right = space
            outRect.left = 0
        }
    }
}