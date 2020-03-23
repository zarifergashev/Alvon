package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.adapter.ColorPickerAdapter
import com.ergashev_zarifjon.macho_man_pro.adapter.EditSubTypeAdapter
import com.ergashev_zarifjon.macho_man_pro.model.EditTypeList
import com.ergashev_zarifjon.macho_man_pro.commons.showMessageDialog
import ja.burhanrashid52.photoeditor.*
import kotlinx.android.synthetic.main.ei_wear_fragment.*
import kotlinx.android.synthetic.main.ei_wear_fragment.view.*


class EIWearFragment : EIBaseFragment() {

    private var type: String? = null
    private val IMAGE_DIALOG = "IMAGE_DIALOG"
    private var tempImageResId: Int? = null

    companion object {
        val ARG_TYPE: String = "ARG_TYPE"

        fun newInstance(type: String) = EIWearFragment().also {
            it.arguments = Bundle().also {
                it.putString(ARG_TYPE, type)
            }
        }
    }

    private val adapter by lazy {
        EditSubTypeAdapter { editType ->
            val f = EIImagesBottomShettDailog.newInstance(editType.type_code) { imageResId ->
                val bm = BitmapFactory.decodeResource(resources, imageResId)
                if (bm != null) {
                    tempImageResId = imageResId
                    mPhotoEditor?.addImage(bm)
                } else {
                    context?.showMessageDialog(R.string.error, R.string.select_picture_is_empty)
                }
            }
            fragmentManager?.let { f.show(it, f.tag) }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { arguments ->
            type = arguments.getString(ARG_TYPE, "")
        }
    }

    fun getType() = arguments?.let { arguments -> arguments.getString(ARG_TYPE, "") }

    var mPhotoEditor: PhotoEditor? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.ei_wear_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
    }

    private val colorPickerAdapter by lazy { ColorPickerAdapter(activity!!) }

    private fun initViews(view: View) {
        mPhotoEditor = PhotoEditor.Builder(view.context, view.ei_iv_wear)
            .setPinchTextScalable(true) // set flag to make text scalable when pinch
            .build() // build photo editor sdk

        listener?.readTempImage { image ->
            view.ei_iv_wear.source.setImageBitmap(image)
        }
        iv_undo.setOnClickListener {
            mPhotoEditor?.undo()
        }
        iv_redo.setOnClickListener {
            mPhotoEditor?.redo()
        }

        mPhotoEditor?.setOnPhotoEditorListener(object : OnPhotoEditorListener {
            override fun onEditTextChangeListener(rootView: View?, text: String?, colorCode: Int) {

            }

            override fun onAddViewListener(viewType: ViewType?, numberOfAddedViews: Int) {
                iv_undo.visibility = View.VISIBLE
                if (mPhotoEditor!!.viewsCount == 0) {
                    iv_redo.visibility = View.GONE
                }

            }

            override fun onRemoveViewListener(numberOfAddedViews: Int) {
                if (numberOfAddedViews == 0)
                    iv_undo.visibility = View.GONE
                if (mPhotoEditor!!.viewsCount == 0) {
                    iv_redo.visibility = View.GONE
                } else
                    iv_redo.visibility = View.VISIBLE
            }

            override fun onRemoveViewListener(viewType: ViewType?, numberOfAddedViews: Int) {
            }

            override fun onStartViewChangeListener(viewType: ViewType?) {

            }

            override fun onStopViewChangeListener(viewType: ViewType?) {
            }
        })

        view.ei_type_recyclerview.adapter = adapter

        if (type != null && type?.isNotEmpty()!!) {
            setRecyclweView(view.context, type!!)
        } else {
            getType()?.let { setRecyclweView(view.context, it) }
        }

        ei_rv_color.adapter = colorPickerAdapter


        view.ei_iv_wear.source.setOnClickListener {
            if (ei_rv_color.visibility == View.VISIBLE) {
                ei_rv_color.visibility = View.GONE
            }
        }

        view.ei_wear_iv_done.setOnClickListener {
            saveImage()
        }

        view.ei_wear_iv_back.setOnClickListener {
            if (iv_redo.visibility == View.GONE && iv_undo.visibility == View.GONE) {
                listener?.onBackPress()
            } else {
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
        mPhotoEditor?.setiPhotoEditAction(object : IPhotoEditAction {
            override fun eraser(view: ImageView?) {
                if (view != null) {
                    try {
                        val bitmap = (view.drawable as BitmapDrawable).bitmap
                        val fragment = EraserFragment.newInstance(bitmap) { image ->
                            view.setImageBitmap(image)
                        }
                        listener?.addFragmentBackStack(R.id.ei_container, fragment, "nullll")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }

            override fun brush(view: ImageView?) {
                if (view != null) {
                    try {
                        /*   val bitmap = (view.drawable as BitmapDrawable).bitmap
                           val fragment = BrushFragment.newInstance(bitmap,
                               IPhotoSaveHelper { bitmap -> view.setImageBitmap(bitmap) })
                           listener?.addFragmentBackStack(R.id.ei_container, fragment, "nullll")*/
                        ei_rv_color.visibility = View.VISIBLE
                        colorPickerAdapter.setOnColorPickerClickListener { colorCode ->

                            val bitmap = (view.drawable as BitmapDrawable)

                            bitmap.setColorFilter(
                                colorCode,
                                PorterDuff.Mode.MULTIPLY
                            )
                            view.setImageDrawable(bitmap)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
        })

    }

    private fun saveImage() {
        listener?.showProgress()
        val setting = SaveSettings.Builder()
            .setClearViewsEnabled(true)
            .setTransparencyEnabled(true)
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
                    //listener?.hideProgress()
                    //    e.message?.let { it1 -> context?.showMessageDialog(R.string.error, it1) }
                    saveImage()

                }
            })
    }

    private fun setRecyclweView(context: Context, type: String) {
        adapter.submitList(EditTypeList.getSubEditTypeList(type, context))
    }

}
