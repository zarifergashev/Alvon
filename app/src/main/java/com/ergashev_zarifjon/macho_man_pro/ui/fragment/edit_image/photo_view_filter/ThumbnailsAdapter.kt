package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.photo_view_filter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.ergashev_zarifjon.macho_man_pro.R
import com.nineoldandroids.view.ViewHelper
import com.nineoldandroids.view.ViewPropertyAnimator

class ThumbnailsAdapter(
    //   private val dataSet: List<ThumbnailItem>,
    private val thumbnailCallback: ThumbnailCallback
) : ListAdapter<ThumbnailItem, RecyclerView.ViewHolder>(object :
    DiffUtil.ItemCallback<ThumbnailItem>() {
    override fun areItemsTheSame(oldItem: ThumbnailItem, newItem: ThumbnailItem) =
        oldItem.filterName == newItem.filterName

    override fun areContentsTheSame(oldItem: ThumbnailItem, newItem: ThumbnailItem) =
        oldItem == newItem
}) {
    private var selectedIndex = 0

    /*init {
        Log.v(TAG, "Thumbnails Adapter has " + dataSet.size + " items")
    }*/


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        Log.v(TAG, "On Create View Holder Called")
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_thumbnail_item_row, viewGroup, false)
        return ThumbnailsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, i: Int) {
        val (image, filter, filterName) = getItem(i)
        Log.v(TAG, "On Bind View Called")
        val thumbnailsViewHolder = holder as ThumbnailsViewHolder
        thumbnailsViewHolder.thumbnail.setImageBitmap(image)
        thumbnailsViewHolder.thumbnail.scaleType = ImageView.ScaleType.FIT_START
        setAnimation(thumbnailsViewHolder.thumbnail, i)
        thumbnailsViewHolder.thumbnail.setOnClickListener { v ->
            if (selectedIndex != i) {
                thumbnailCallback.onThumbnailClick(filter)
                selectedIndex = i
                notifyDataSetChanged()
            }
        }

        thumbnailsViewHolder.filterName.text = filterName

        if (selectedIndex == i) {
            thumbnailsViewHolder.filterName.setTextColor(
                ContextCompat.getColor(
                    thumbnailsViewHolder.filterName.context,
                    R.color.colorAccent
                )
            )
        } else {
            thumbnailsViewHolder.filterName.setTextColor(
                ContextCompat.getColor(
                    thumbnailsViewHolder.filterName.context,
                    R.color.filter_label_normal
                )
            )
        }
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        run {
            ViewHelper.setAlpha(viewToAnimate, .0f)
            ViewPropertyAnimator.animate(viewToAnimate).alpha(1f).setDuration(250).start()
            lastPosition = position
        }
    }

    class ThumbnailsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var thumbnail: ImageView = v.findViewById(R.id.thumbnail)
        var filterName: TextView = v.findViewById(R.id.filter_name)

    }

    companion object {
        private val TAG = "THUMBNAILS_ADAPTER"
        private var lastPosition = -1
    }
}
