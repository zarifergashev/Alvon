package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.photo_view_filter

import android.content.Context
import android.graphics.Bitmap

import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.photo_view_filter.ThumbnailItem
import java.util.*

import java.util.Collections.synchronizedList



/**
 * @author Varun on 30/06/15.
 *
 *
 * Singleton Class Used to Manage filters and process them all at once
 */
class ThumbnailsManager {
    @Volatile
    private var filterThumbs: MutableList<ThumbnailItem> = synchronizedList(mutableListOf())
    @Volatile
    private var processedThumbs: MutableList<ThumbnailItem> = synchronizedList(mutableListOf())
    @Volatile
    private var isProcess = false

    fun addThumb(thumbnailItem: ThumbnailItem) {
        if (!isProcess) {
            val filterThum = filterThumbs.filter { it.filterName == thumbnailItem.filterName }
            if (filterThum.isEmpty())
                filterThumbs.add(thumbnailItem)
        }
    }

    suspend fun processThumbs(context: Context): List<ThumbnailItem> {
        isProcess = true
        filterThumbs.forEach { thumb ->
            // scaling down the image
            val size = context.resources.getDimension(R.dimen.thumbnail_size)
            thumb.image = thumb.image?.let {
                Bitmap.createScaledBitmap(
                    it,
                    size.toInt(),
                    size.toInt(),
                    false
                )
            }
            thumb.image = thumb.filter.processFilter(thumb.image)
            // cropping circle
            // thumb.image = GeneralUtils.generateCircularBitmap(thumb.image);
            processedThumbs.add(thumb)
        }
        /*for (thumb in filterThumbs) {
            // scaling down the image
            val size = context.resources.getDimension(R.dimen.thumbnail_size)
            thumb.image =
                Bitmap.createScaledBitmap(thumb.image!!, size.toInt(), size.toInt(), false)
            thumb.image = thumb.filter.processFilter(thumb.image)
            // cropping circle
            // thumb.image = GeneralUtils.generateCircularBitmap(thumb.image);
            processedThumbs.add(thumb)
        }*/
        isProcess = false
        return processedThumbs
    }

    fun clearThumbs() {
        if (!isProcess) {
            filterThumbs.clear()
            processedThumbs.clear()
        }
    }
    companion object{
        private var INSTANCE:ThumbnailsManager?=null

        fun instance()= INSTANCE?: ThumbnailsManager().apply {
            INSTANCE=this
        }
    }
}
