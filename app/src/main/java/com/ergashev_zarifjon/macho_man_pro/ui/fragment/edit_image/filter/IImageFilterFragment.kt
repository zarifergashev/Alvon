package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.filter

import android.graphics.Bitmap
import com.ergashev_zarifjon.macho_man_pro.model.EditType

interface IImageFilterFragment {
    fun generateImage(type:EditType,bitmap: Bitmap):Bitmap?

    fun showProgress()

    fun hideProgress()
}