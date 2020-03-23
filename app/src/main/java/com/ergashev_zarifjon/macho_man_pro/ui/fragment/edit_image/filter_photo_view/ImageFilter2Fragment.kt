package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.filter_photo_view

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.image_filter.FilterListener
import com.ergashev_zarifjon.macho_man_pro.image_filter.FilterViewAdapter
import com.ergashev_zarifjon.macho_man_pro.commons.showMessageDialog
import com.ergashev_zarifjon.macho_man_pro.data.AppDatabas
import com.ergashev_zarifjon.macho_man_pro.model.EditType
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.EIBaseFragment
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.EIWearFragment
import ja.burhanrashid52.photoeditor.OnSaveBitmap
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.PhotoFilter
import ja.burhanrashid52.photoeditor.SaveSettings
import kotlinx.android.synthetic.main.ei_filter2_fragment.view.*


class ImageFilter2Fragment : EIBaseFragment(), IImageFilter2Fragment {
    override fun generateImage(type: EditType, bitmap: Bitmap): Bitmap? {
        return null
    }

    override fun showProgress() {
        listener?.showProgress()
    }

    override fun hideProgress() {
        listener?.hideProgress()
    }

    private var type: String? = null
    private var originalBitmap: Bitmap? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        inflater.inflate(
            R.layout.ei_filter2_fragment, container, false
        )


    private val filter2Adapter by lazy {
        FilterViewAdapter(object : FilterListener {
            override fun onFilterSelected(photoFilter: PhotoFilter) {
                mPhotoEditor?.setFilterEffect(photoFilter)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { arguments ->
            type = arguments.getString(EIWearFragment.ARG_TYPE, "")
        }
    }

    fun getType() = arguments?.let { arguments -> arguments.getString(EIWearFragment.ARG_TYPE, "") }
    var mPhotoEditor: PhotoEditor? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initObjects()
    }

    private val factory by lazy {
        ImageFilter2ViewModelFactory(AppDatabas.getDatabaseInstance(activity!!.applicationContext))
    }
    private val viewModel by lazy {
        ViewModelProviders.of(this, factory).get(ImageFilter2ViewModel::class.java).also {
            it.initView(this)
        }
    }

    private fun initObjects() {
        viewModel.typs.observe(this, Observer { filterMenuList ->
            val mock = FilterViewAdapter.setupFilters()
            val result = mutableListOf<Pair<String, PhotoFilter>>()

            mock.forEach {
                val filterName = it.second.name
                val filterMenu = filterMenuList.filter { it.code == filterName }
                val imgUrl = if (filterMenu.isNotEmpty()) filterMenu.first().imgUrl else it.first
                result.add(Pair(first = imgUrl, second = it.second))
            }
            filter2Adapter.setData(result)
        })
    }

    private fun initViews(view: View) {

        mPhotoEditor = PhotoEditor.Builder(view.context, view.ei_pe_wear)
            .setPinchTextScalable(true) // set flag to make text scalable when pinch
            .build() // build photo editor sdk


        listener?.readTempImage { image ->
            originalBitmap = image
            view.ei_pe_wear.source.setImageBitmap(image)
        }

        view.ei_wear_iv_done.setOnClickListener {
            listener?.showProgress()
            val setting = SaveSettings.Builder()
                .setClearViewsEnabled(true)
                .setTransparencyEnabled(false)
                .build()
            mPhotoEditor?.saveAsBitmap(setting,
                object : OnSaveBitmap {
                    override fun onBitmapReady(saveBitmap: Bitmap) {
                        listener?.saveTempImage(saveBitmap) {
                            listener?.hideProgress()
                            listener?.onBackPress()
                        }
                    }

                    override fun onFailure(e: Exception) {
                        e.message?.let { it1 -> context?.showMessageDialog(R.string.error, it1) }
                    }
                })
        }

        if (type != null && type?.isNotEmpty()!!) {
            view.ei_type_recyclerview.adapter = filter2Adapter

        } else {
            getType()?.let {
                view.ei_type_recyclerview.adapter = filter2Adapter
            }
        }



        view.ei_wear_iv_back.setOnClickListener {
            context?.showMessageDialog(
                R.string.warning,
                R.string.are_you_sure_want_to_exit,
                positiveBtnText = R.string.yes,
                positiveBtnAction = { listener?.onBackPress() },
                negativBtnText = R.string.no,
                negativBtnAction = {
                }
            )
        }
    }

    companion object {
        val ARG_TYPE: String = "ARG_TYPE"

        fun newInstance(type: String) = ImageFilter2Fragment().also {
            it.arguments = Bundle().also {
                it.putString(ARG_TYPE, type)
            }
        }
    }


}