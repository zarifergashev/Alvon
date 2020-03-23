package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.photo_view_filter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.*
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.commons.resizeByMaximum
import com.ergashev_zarifjon.macho_man_pro.data.entity.FilterItemMenu
import com.ergashev_zarifjon.macho_man_pro.data.repositorys.FilterMenuRepository
import com.zomato.photofilters.FilterPack
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.util.*

class EIPhotoFilterViewModel : ViewModel() {

    private val _filterList = MutableLiveData<List<ThumbnailItem>>()
    val filterList: LiveData<List<ThumbnailItem>> get() = _filterList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> get() = _progress

    private val _image = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap> get() = _image

    private val brightnessFinal = MutableLiveData<Int>()
    private val contrastFinal = MutableLiveData<Float>()
    private val saturationFinal = MutableLiveData<Float>()

    private var originalImage: Bitmap? = null

    private var view: IEIPhotFilter? = null

    fun initView(view: IEIPhotFilter) {
        this.view = view
    }

    fun setOriginalImage(bitmap: Bitmap) {
        this.originalImage = bitmap
    }

    fun dowloandList() {
        if (originalImage != null) {
            generateFilterList(originalImage!!)
        } else {
            view?.getOriginalImage {
                originalImage = it
                generateFilterList(it)
            }
        }
    }

    private fun generateFilterList(originalImage: Bitmap) {
        view?.getContexts()?.let { context ->
            try {
                _progress.postValue(true)

                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val thumbnailItemList = mutableListOf<ThumbnailItem>()
                        var filterThumbs: MutableList<ThumbnailItem> =
                            Collections.synchronizedList(mutableListOf())
                        var processedThumbs: MutableList<ThumbnailItem> =
                            Collections.synchronizedList(mutableListOf())

                        val resizeBitmap = originalImage.resizeByMaximum(400)

                        // add normal bitmap first
                        val thumbnailItem = ThumbnailItem()
                        thumbnailItem.image = resizeBitmap
                        thumbnailItem.filterName =
                            view?.getContexts()?.getString(R.string.filter_normal)
                        filterThumbs.add(thumbnailItem)

                        val filters = FilterPack.getFilterPack(view?.getContexts())

                        for (filter in filters) {
                            val tI = ThumbnailItem()
                            tI.image = resizeBitmap
                            tI.filter = filter
                            tI.filterName = filter.name
                            filterThumbs.add(tI)
                        }
                        filterThumbs.forEach { thumb ->
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
                            processedThumbs.add(thumb)
                        }
                        _filterList.postValue(processedThumbs)
                        _progress.postValue(false)

                    }catch (e:Exception){

                    }
                }
            } catch (e: Exception) {
                _progress.postValue(false)
                _errorMessage.postValue(e.message)
            }
        }
        if (view == null || view?.getContexts() == null) {
            _errorMessage.postValue("Same error Place replase open this view or Application")
        }
    }


    fun onFilterSelected(filter: Filter) {
        if (originalImage != null) {
            try {
                _progress.postValue(true)
                viewModelScope.launch(Dispatchers.IO) {
                    val filteredImage = originalImage!!.copy(Bitmap.Config.ARGB_8888, true)
                    val fil = filter.processFilter(filteredImage)
                    withContext(Dispatchers.Main) {
                        _progress.postValue(false)
                        _image.value = fil
                    }
                }
            } catch (e: Exception) {
                _progress.postValue(false)
                _errorMessage.postValue(e.message)
            }

        } else {
            view?.getOriginalImage { image ->
                try {
                    originalImage = image
                    _progress.postValue(true)
                    viewModelScope.launch(Dispatchers.IO) {
                        val filteredImage = image.copy(Bitmap.Config.ARGB_8888, true)
                        val fil = filter.processFilter(filteredImage)
                        withContext(Dispatchers.Main) {
                            _progress.postValue(false)
                            _image.value = fil

                        }
                    }
                } catch (e: Exception) {
                    _progress.postValue(false)
                    _errorMessage.postValue(e.message)
                }
            }
        }
    }

    fun onBrightnessChanged(brightness: Int) {
        brightnessFinal.value = brightness
        val myFilter = Filter()
        myFilter.addSubFilter(BrightnessSubFilter(brightness))
        onFilterSelected(myFilter)

    }

    fun onSaturationChanged(saturation: Float) {
        saturationFinal.value = saturation
        val myFilter = Filter()
        myFilter.addSubFilter(SaturationSubfilter(saturation))
        onFilterSelected(myFilter)
    }

    fun onContrastChanged(contrast: Float) {
        contrastFinal.value = contrast
        val myFilter = Filter()
        myFilter.addSubFilter(ContrastSubFilter(contrast))
        onFilterSelected(myFilter)
    }

    fun onEditStarted() {

    }

    fun onEditCompleted() {
        val myFilter = Filter()
        brightnessFinal.value?.let { myFilter.addSubFilter(BrightnessSubFilter(it)) }
        contrastFinal.value?.let { myFilter.addSubFilter(ContrastSubFilter(it)) }
        saturationFinal.value?.let { myFilter.addSubFilter(SaturationSubfilter(it)) }
        onFilterSelected(myFilter)
    }
}