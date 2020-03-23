package com.ergashev_zarifjon.macho_man_pro.ui.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.ui.activity.IMainActivity
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_about.view.*


class AboutFragment : Fragment() {

    private var listener: IMainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
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
        view.tb_main_activity_toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        Glide.with(view.context).load(R.drawable.img_app).into(view.iv_app_img)
        initView()
    }

    private fun initView() {
        cv_more_apps.setOnClickListener {
            val appPackageName = it.context.packageName
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (anfe: android.content.ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }
        cv_private_politic.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.freeprivacypolicy.com/privacy/view/3690013d3710880c796b81874a90b8e8")
                )
            )
        }

        cv_share.setOnClickListener {
            val appPackageName = it.context.packageName
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(
                Intent.EXTRA_SUBJECT,
                "I have found something called ${it.context.getString(R.string.app_name)}. I recommend you to try this meme maker application ! "
            )
            i.putExtra(
                Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=$appPackageName"
            )
            startActivity(Intent.createChooser(i, "Share URL"))
        }
        cv_rate_use.setOnClickListener {
            val appPackageName = it.context.packageName
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (anfe: android.content.ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        fun newInstance(): Fragment {
            return AboutFragment()
        }
    }
}
