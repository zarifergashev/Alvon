package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.photo_view_filter

import android.content.Context
import android.graphics.Bitmap

interface IEIPhotFilter {
    fun getOriginalImage(doneAction: (Bitmap) -> Unit)
    fun getContexts(): Context
}