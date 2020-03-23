package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.filter

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ergashev_zarifjon.macho_man_pro.App
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.adapter.EditImageFilterAdapter
import com.ergashev_zarifjon.macho_man_pro.image_filter.ImageFilter
import com.ergashev_zarifjon.macho_man_pro.model.EditType
import com.ergashev_zarifjon.macho_man_pro.model.EditTypeList
import com.ergashev_zarifjon.macho_man_pro.commons.showMessageDialog
import com.ergashev_zarifjon.macho_man_pro.data.AppDatabas
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.ProgresDialog
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.EIBaseFragment
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.EIWearFragment
import ja.burhanrashid52.photoeditor.OnSaveBitmap
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.PhotoFilter
import ja.burhanrashid52.photoeditor.SaveSettings
import kotlinx.android.synthetic.main.ei_filter2_fragment.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


class ImageFilterFragment : EIBaseFragment(), IImageFilterFragment {
    override fun hideProgress() {
     /*   if (progress.isAdded) {
            progress.dismiss()
        }*/
    }

    override fun showProgress() {
        /*if (!progress.isAdded) {
            fragmentManager?.let { it1 -> progress.open(it1,"tasdsdddd") }
        }
*/
    }

    override fun generateImage(type: EditType, bitmap: Bitmap): Bitmap? {
        return runBlocking(Dispatchers.IO) { filtered(type.type_code, originalBitmap) }
    }

    private var type: String? = null
    private var originalBitmap: Bitmap? = null


    private val factory by lazy {
        ImageFilterViewModelFactory(AppDatabas.getDatabaseInstance(activity!!.applicationContext))
    }
    private val viewModel by lazy {
        ViewModelProviders.of(this,factory).get(ImageFilterViewModel::class.java).also {
            it.initView(this)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?) =
        inflater.inflate(
            R.layout.ei_filter2_fragment, container, false
        )

    private val adapter by lazy {
        EditImageFilterAdapter { editType ->

            val image = runBlocking(Dispatchers.IO) { filtered(editType.code, originalBitmap) }
            if (image != null) {
                view?.ei_pe_wear?.source?.setImageBitmap(image)
            } else {
                try {
                    val t = editType.code as PhotoFilter
                    mPhotoEditor?.setFilterEffect(t)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }


    private suspend fun filtered(
        type_code: String,
        originalBitmap: Bitmap?
    ): Bitmap? {
        return when (type_code) {
            EditTypeList.BLUR_1 -> originalBitmap?.let { ImageFilter.filter1(it) }
            EditTypeList.BLUR_2 -> originalBitmap?.let { ImageFilter.filter9(it) }
            EditTypeList.BLUR_3 -> originalBitmap?.let { ImageFilter.filter6(it) }
            EditTypeList.BLUR_4 -> originalBitmap?.let { ImageFilter.filter7(it) }
            //EditTypeList.BLUR_5 -> originalBitmap?.let { ImageFilter.filter5(it) }
            EditTypeList.BLUR_6 -> originalBitmap?.let { ImageFilter.filter8(it) }
            EditTypeList.BLUR_7 -> originalBitmap?.let { ImageFilter.filter2(it) }
            EditTypeList.BLUR_8 -> originalBitmap?.let { ImageFilter.filter3(it) }
            EditTypeList.BLUR_9 -> originalBitmap?.let { ImageFilter.filter4(it) }
            EditTypeList.BLUR_10 -> originalBitmap?.let { ImageFilter.filter5(it) }
            else -> null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { arguments ->
            type = arguments.getString(EIWearFragment.ARG_TYPE, "")
        }
    }

    fun getType() = arguments?.let { arguments -> arguments.getString(EIWearFragment.ARG_TYPE, "") }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initObjects(view)
    }

    private fun initViews(view: View) {
        mPhotoEditor = PhotoEditor.Builder(view.context, view.ei_pe_wear)
            .setPinchTextScalable(true) // set flag to make text scalable when pinch
            .build() // build photo editor sdk

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

        view.ei_wear_iv_back.setOnClickListener {
            context?.showMessageDialog(
                R.string.warning,
                R.string.are_you_sure_want_to_exit,
                positiveBtnText = R.string.yes,
                positiveBtnAction = {
                    listener?.onBackPress()
                },
                negativBtnText = R.string.no,
                negativBtnAction = {

                }
            )
        }
    }

    var mPhotoEditor: PhotoEditor? = null

    companion object {
        val ARG_TYPE: String = "ARG_TYPE"

        fun newInstance(type: String) = ImageFilterFragment().also {
            it.arguments = Bundle().also {
                it.putString(ARG_TYPE, type)
            }
        }
    }

    private val progress by lazy {
        ProgresDialog()
    }

    fun initObjects(view: View) {
        listener?.readTempImage { image ->
            originalBitmap = image
            view.ei_pe_wear.source.setImageBitmap(image)

            if (type != null && type?.isNotEmpty()!!) {

                setRecyclweView(view.context, type!!)
                view.ei_type_recyclerview.adapter = adapter
            } else {
                getType()?.let {
                    setRecyclweView(view.context, it)
                    view.ei_type_recyclerview.adapter = adapter
                }
            }
        }

        viewModel.typs.observe(this, Observer {
            adapter.submitList(it)
        })

    }

    private fun setRecyclweView(context: Context, type: String) {
        /*viewModel.generateFilterAvatar(
            originalBitmap!!,
            EditTypeList.getSubEditTypeList(type, context)
        )*/

    }

}
