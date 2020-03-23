package com.ergashev_zarifjon.macho_man_pro.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.ui.activity.ImageFullScreenActivity
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.EIBaseFragment
import kotlinx.android.synthetic.main.share_fragment.*
import kotlinx.android.synthetic.main.share_fragment.view.*
import java.io.File

class ShareFragment : EIBaseFragment() {

    private var imagePath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        inflater.inflate(R.layout.share_fragment, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { arguments ->
            imagePath = arguments.getString(ARG_TYPE, "")
        }
    }

    fun getType() = arguments?.let { arguments -> arguments.getString(ARG_TYPE, "") }

    override fun onStart() {
        super.onStart()
        val file = File(imagePath)
        Glide.with(this)
            .load(file)
            .into(shf_iv_main)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.shf_share_face.setOnClickListener {
            listener?.shareFacebook(File(imagePath))
        }
        view.shf_share_inta.setOnClickListener {
            listener?.shareInstagram(File(imagePath))
        }
        view.shf_share_tvet.setOnClickListener {
            listener?.shareTwitter(File(imagePath))
        }
        view.shf_share_other.setOnClickListener {
            listener?.shareOther(File(imagePath))
        }
        view.shf_iv_main.setOnClickListener {
            activity?.let { imagePath?.let { it1 -> ImageFullScreenActivity.open(it, it1) } }
        }
    }

    companion object {
        val ARG_TYPE: String = "ARG_TYPE"

        fun newInstance(imagePath: String) = ShareFragment().also {
            it.arguments = Bundle().also {
                it.putString(ARG_TYPE, imagePath)
            }
        }
    }
}