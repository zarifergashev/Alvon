package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.photo_view_filter

import com.zomato.photofilters.imageprocessors.Filter

interface ThumbnailCallback {
    fun onThumbnailClick(filter: Filter)
}
