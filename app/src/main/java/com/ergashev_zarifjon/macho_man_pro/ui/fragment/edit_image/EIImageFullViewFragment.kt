package com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ergashev_zarifjon.macho_man_pro.R
import kotlinx.android.synthetic.main.ei_image_full_view_fragment.*
import kotlinx.android.synthetic.main.ei_image_full_view_fragment.view.*

class EIImageFullViewFragment : EIBaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.ei_image_full_view_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        initObjects()
    }

    private fun initViews(view: View) {

        view.ei_image_full_view_btn_back.setOnClickListener {
            listener?.onBackPress()
        }
        view.ei_image_full_view_iv_main.setOnClickListener {
            if (ei_image_full_view_toolbar_container.visibility == View.VISIBLE) {
                ei_image_full_view_toolbar_container.visibility = View.GONE
            } else {
                ei_image_full_view_toolbar_container.visibility = View.VISIBLE
            }
        }
    }


    private fun initObjects() {
        listener?.readTempImage { bitmap ->
            ei_image_full_view_iv_main.setImageBitmap(bitmap)
        }
    }

}