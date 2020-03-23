package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.filter_photo_view

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

class ImageFilter2ViewModel(private val databas: AppDatabas) : ViewModel() {

    var typs: LiveData<List<FilterItemMenu>> =
        databas.filterItemMenuDao().getFilterItemMenu(parentId = EditTypeList.FILTERS)


    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> = _progress


    private var view: IImageFilter2Fragment? = null

    fun initView(view: IImageFilter2Fragment) {
        this.view = view
    }

/*
    fun generateFilterAvatar(bitmap: Bitmap, editTyps: MutableList<EditType>) {
        view?.showProgress()
        viewModelScope.launch {

            val resizeBitmap = bitmap.resizeByMaximum(400)

            view?.let { view ->
                val result = mutableListOf<FilterEditType>()
                editTyps.forEach {
                    val img =
                        view.generateImage(it, resizeBitmap.copy(Bitmap.Config.ARGB_4444, true))
                    result.add(
                        FilterEditType(
                            type_code = it.type_code,
                            type_name = it.type_name,
                            type_img = img
                        )
                    )

                }
                view.hideProgress()
                _typs.postValue(result)

            }

        }
    }*/

}