package com.ergashev_zarifjon.macho_man_pro.ui.fragment.self_picture

import android.Manifest
import android.os.Environment
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergashev_zarifjon.macho_man_pro.commons.getFileSizeKbOrMb
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class PictureViewModel : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> = _progress

    private val _pictures = MutableLiveData<List<SelfPicture>>()
    val pictures: LiveData<List<SelfPicture>> = _pictures

    private var view: IPictureFragment? = null

    fun initView(view: IPictureFragment) {
        this.view = view
    }

    fun dowlaondSelfPicture() {
        viewModelScope.launch {
            view?.checkPermission({
                view?.btnPermission(View.GONE)
                val sdCard = Environment.getExternalStorageDirectory()
                var directory = File(sdCard.absolutePath + "/Alvon")
                if (!directory.exists()) {
                    directory = File(sdCard.absolutePath)
                }
                val files = directory.listFiles()
                if (files.isNullOrEmpty().not()) {
                    val data = ArrayList<SelfPicture>()
                    val fileCount = files.size

                    for (i in (0 until fileCount)) {
                        if (files[i].name.contains("alvon")) {
                            data.add(
                                SelfPicture(
                                    imageUrl = files[i].absolutePath,
                                    imageSize = files[i].getFileSizeKbOrMb(),
                                    createDate = Date(files[i].lastModified())
                                )
                            )
                        }
                    }
                    if (data.size > 0) {
                        view?.rvPicture(View.VISIBLE)
                        view?.emptyImage(View.INVISIBLE)
                        _pictures.postValue(data)
                    } else {
                        view?.rvPicture(View.INVISIBLE)
                        view?.emptyImage(View.VISIBLE)
                    }

                } else {
                    view?.rvPicture(View.INVISIBLE)
                    view?.emptyImage(View.VISIBLE)
                }
            }, {
                view?.btnPermission(View.VISIBLE)
                view?.rvPicture(View.GONE)
            }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

}