package com.ergashev_zarifjon.macho_man_pro.ui.fragment.main_fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.ui.activity.IMainActivity
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    private var listener: IMainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IMainActivity) {
            listener = context
        } else {
            throw RuntimeException("$context must implement com.example.alvon.ui.activity.IMainActivity")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.iv_camera.setOnClickListener {
            listener?.openCamera()
        }
        view.ic_picture.setOnClickListener {
            listener?.choseFile()
        }

    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object{
        fun newInstance():Fragment{
            return MainFragment()
        }
    }


}
