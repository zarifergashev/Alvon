package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.adapter.EditSubTypeImagesAdapter
import com.ergashev_zarifjon.macho_man_pro.model.EditTypeList
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.ei_images_dialog.view.*


class EIImagesBottomShettDailog : BottomSheetDialogFragment() {
    private var type: String? = null
    var selectImage: ((Int) -> Unit)? = null

    fun setSelectListener(listener: ((Int) -> Unit)) {
        this.selectImage = listener
    }

    companion object {
        val ARG_TYPE: String = "ARG_TYPE"
        fun newInstance(type: String, listener: ((Int) -> Unit)) = EIImagesBottomShettDailog().also {
            it.arguments = Bundle().also {
                it.putString(ARG_TYPE, type)
            }
            it.setSelectListener(listener)
        }
    }

    private val adapter by lazy {
        EditSubTypeImagesAdapter { editType ->
            selectImage?.invoke(editType.type_img)
            dissmiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { arguments ->
            type = arguments.getString(ARG_TYPE, "")
        }
    }

    fun getType() = arguments?.let { arguments ->
        arguments.getString(ARG_TYPE, "")
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        if (dialog != null) {
            dialog.setOnShowListener {
                val d = dialog
                val bottomSheet = d.findViewById<FrameLayout>(R.id.design_bottom_sheet)
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)  // setContentView called here
        (view!!.parent as View).setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.ei_images_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.image_list.adapter = adapter

        if (type != null && type?.isNotEmpty()!!) {
            setRecyclweView(view.context, type!!)
        } else {
            getType()?.let { setRecyclweView(view.context, it) }
        }
        view.ei_wear_iv_back.setOnClickListener {
            dissmiss()
        }

    }

    fun dissmiss() {
        dialog?.dismiss()
    }

    private fun setRecyclweView(context: Context, type: String) {
        adapter.submitList(EditTypeList.getSubEditImageTypeList(type, context))
    }

}
