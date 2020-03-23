package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image

import android.Manifest
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.adapter.EditTypeAdapter
import com.ergashev_zarifjon.macho_man_pro.model.EditTypeList
import com.ergashev_zarifjon.macho_man_pro.commons.showMessageDialog
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.ShareFragment
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.filter.ImageFilterFragment
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.filter_photo_view.ImageFilter2Fragment
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.photo_view_filter.EIPhotFilter
import ja.burhanrashid52.photoeditor.PhotoEditor
import kotlinx.android.synthetic.main.ei_main_fragment.*
import kotlinx.android.synthetic.main.ei_main_fragment.view.*
import java.io.File
import java.io.IOException

class EIMainFragmanet : EIBaseFragment() {

    private val adapter by lazy {
        EditTypeAdapter { editType ->
            if (editType.type_code == EditTypeList.BLUR) {
                listener?.replaceFragmentBackStack(
                    R.id.ei_container,
                    ImageFilterFragment.newInstance(editType.type_code)
                )
            } else if (editType.type_code == EditTypeList.FILTERS) {
                listener?.replaceFragmentBackStack(
                    R.id.ei_container, EIPhotFilter.newInstance(editType.type_code)
                )
            } else if (editType.type_code == EditTypeList.SHARE) {
                shareImage(false)
            } else {
                listener?.replaceFragmentBackStack(
                    R.id.ei_container,
                    EIWearFragment.newInstance(editType.type_code)
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View = inflater.inflate(R.layout.ei_main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        view.ei_type_recyclerview.adapter = adapter
        adapter.submitList(EditTypeList.getEditTypeList(view.context))

        view.btn_back_main_fragment.setOnClickListener {
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
        view.ei_iv_full_view.setOnClickListener {
            listener?.replaceFragmentBackStack(R.id.ei_container, EIImageFullViewFragment())
        }

        view.ei_iv_save.setOnClickListener {
            shareImage(true)
        }
    }

    private fun shareImage(clearBackStack: Boolean) {
        saveImage { image_path ->
            if (clearBackStack) {
                listener?.replaceFragment(
                    R.id.ei_container,
                    ShareFragment.newInstance(image_path)
                )
            } else {
                listener?.replaceFragmentBackStack(
                    R.id.ei_container,
                    ShareFragment.newInstance(image_path)
                )
            }
        }
    }

    private fun saveImage(saveAction: (String) -> Unit) {
        if (listener != null && listener!!.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            val sdCard = Environment.getExternalStorageDirectory()
            var dir = File(sdCard.absolutePath + "/Alvon")
            if (!dir.exists() && !dir.mkdir()) {
                dir = File(sdCard.absolutePath)
            }
            val fileName = String.format("alvon_%d.png", System.currentTimeMillis())
            val file = File(dir, fileName)
            try {
                file.createNewFile()
                listener?.readTempImage { bitmap ->
                    bitmap?.let {
                        listener?.showProgress()
                        listener?.saveAsFile(
                            bitmap = it,
                            imagePath = file.absolutePath,
                            onSaveListener = object : PhotoEditor.OnSaveListener {
                                override fun onSuccess(imagePath: String) {
                                    listener?.hideProgress()
                                    saveAction.invoke(imagePath)
                                }

                                override fun onFailure(exception: java.lang.Exception) {
                                    listener?.hideProgress()
                                    if (file.exists()) {
                                        file.delete()
                                    }
                                }
                            })
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                listener?.hideProgress()
                activity?.showMessageDialog(R.string.error, e.message.toString())
            }

            val path = arrayOf(file.absolutePath)
            val mimeType = arrayOf("image/jpeg")
            MediaScannerConnection.scanFile(activity, path, mimeType, null)
        }
    }

    override fun onStart() {
        super.onStart()
        listener?.readTempImage {
            Glide.with(this)
                .load(it)
                .into(ei_iv_main)
        }
    }
}