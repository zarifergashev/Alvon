package com.ergashev_zarifjon.macho_man_pro.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.ergashev_zarifjon.macho_man_pro.R


class ProgresDialog : DialogFragment() {
    fun open(fragmentManager: FragmentManager, tag: String = "pro_dialog") {
        val fragment = ProgresDialog()
        fragment.show(fragmentManager, tag)
    }

    override fun onStart() {
        super.onStart()
        if (dialog == null) {
            return
        }
        dialog!!.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog!!.setCancelable(false)
        dialog!!.setTitle(null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.progress_dialog, container, false)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setTitle(null)
    }
}