package com.ergashev_zarifjon.macho_man_pro.ui.fragment.self_picture

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.ui.activity.IMainActivity
import kotlinx.android.synthetic.main.ei_image_full_view_fragment.*
import kotlinx.android.synthetic.main.ei_image_full_view_fragment.view.ei_image_full_view_btn_back
import kotlinx.android.synthetic.main.ei_image_full_view_fragment.view.ei_image_full_view_iv_main

class ImageFullViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        inflater.inflate(R.layout.ei_image_full_view_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        initObjects()
    }

    private fun initViews(view: View) {

        view.ei_image_full_view_btn_back.setOnClickListener {
            listener?.onBackPressed()
        }
        view.ei_image_full_view_iv_main.setOnClickListener {
            if (ei_image_full_view_toolbar_container.visibility == View.VISIBLE) {
                ei_image_full_view_toolbar_container.visibility = View.GONE
            } else {
                ei_image_full_view_toolbar_container.visibility = View.VISIBLE
            }
        }

    }

    private val imageUrl by lazy {
        arguments?.getString(PICTURE_URL) ?: ""

    }

    private var listener: IMainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IMainActivity) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    private fun initObjects() {
        if (imageUrl.isEmpty())
            listener?.onBackPressed()

        Glide.with(this).load(imageUrl).into(ei_image_full_view_iv_main)
    }

    companion object {
        const val PICTURE_URL = "PICTURE_URL"
        fun newInstance(selfPicture: SelfPicture): Fragment {
            return ImageFullViewFragment().also {
                it.arguments = Bundle().also {
                    it.putString(PICTURE_URL, selfPicture.imageUrl)
                }
            }

        }
    }

}