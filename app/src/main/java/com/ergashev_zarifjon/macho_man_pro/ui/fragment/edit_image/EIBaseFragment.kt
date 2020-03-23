package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image

import android.content.Context
import androidx.fragment.app.Fragment
import com.ergashev_zarifjon.macho_man_pro.ui.activity.IEditImageActivity

open class EIBaseFragment : Fragment() {
     var listener: IEditImageActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as IEditImageActivity
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}