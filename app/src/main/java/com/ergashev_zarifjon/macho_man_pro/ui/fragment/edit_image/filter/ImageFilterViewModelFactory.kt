package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.filter

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.ergashev_zarifjon.macho_man_pro.model.EditType
import com.ergashev_zarifjon.macho_man_pro.model.FilterEditType
import com.ergashev_zarifjon.macho_man_pro.commons.resizeByMaximum
import com.ergashev_zarifjon.macho_man_pro.data.AppDatabas
import com.ergashev_zarifjon.macho_man_pro.data.entity.FilterItemMenu
import com.ergashev_zarifjon.macho_man_pro.model.EditTypeList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ImageFilterViewModelFactory(private val databas: AppDatabas) :ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>) : T = with(modelClass) {
        when{
            isAssignableFrom(ImageFilterViewModel::class.java) -> ImageFilterViewModel(databas)
            else -> IllegalAccessException("unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}