package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.photo_view_filter

import android.graphics.Bitmap

import com.zomato.photofilters.imageprocessors.Filter

data class ThumbnailItem(
    var image: Bitmap? = null,
    var filter: Filter= Filter(),
    var filterName: String? = ""
)


