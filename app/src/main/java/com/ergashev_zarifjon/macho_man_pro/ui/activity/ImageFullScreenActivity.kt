package com.ergashev_zarifjon.macho_man_pro.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.commons.Util
import com.ergashev_zarifjon.macho_man_pro.commons.showMessageDialog
import kotlinx.android.synthetic.main.img_full_screen_activity.*
import java.io.File

class ImageFullScreenActivity : AppCompatActivity() {

    fun getImageUrl(): Uri? {
        val intent = intent
        var imageURI: Uri? = null
        if (intent.action != null && intent.action == Intent.ACTION_SEND) {
            val extras = intent.extras
            if (extras!!.containsKey(Intent.EXTRA_STREAM)) {
                imageURI = extras.getParcelable<Parcelable>(Intent.EXTRA_STREAM) as Uri?
            }
        } else if (intent.action != null && intent.action == Intent.ACTION_VIEW) {
            imageURI = intent.data
        } else {
            imageURI = intent.getParcelableExtra<Parcelable>("image") as Uri?
        }
        return if (intent.getStringExtra(IMAGE_URL) != null && intent.getStringExtra(IMAGE_URL).isNotEmpty()) {
            Uri.parse(intent.getStringExtra(IMAGE_URL))
        } else {
            imageURI
        }
    }

    fun getImagePath() = intent.getStringExtra(IMAGE_URL) ?: ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.img_full_screen_activity)
        if (getImagePath().isNotEmpty()) {
            Glide.with(this).load(getImagePath()).into(ei_image_full_view_iv_main)
        } else {
            Glide.with(this).load(getImageUrl()).into(ei_image_full_view_iv_main)
        }
        ei_image_full_view_btn_back.setOnClickListener {
            finish()
        }
        ei_image_full_view_iv_main.setOnClickListener {
            if (ei_image_full_view_toolbar_container.visibility == View.VISIBLE) {
                ei_image_full_view_toolbar_container.visibility = View.GONE
            } else {
                ei_image_full_view_toolbar_container.visibility = View.VISIBLE
            }
        }
        ei_image_full_view_btn_delete.setOnClickListener {
            val path = if (getImagePath().isNotEmpty()) {
                getImagePath()
            } else {
                getImageUrl()?.path ?: ""
            }
            Util.deleteFile(path, { finish() }, { error ->
                showMessageDialog(
                    R.string.error,
                    error,
                    positiveBtnText = R.string.ok,
                    positiveBtnAction = {

                    })
            })

        }

        ei_image_full_view_btn_edit.setOnClickListener {
            val uri = if (getImagePath().isNotEmpty()) {
                Uri.fromFile(File(getImagePath()))
            } else {
                getImageUrl()
            }
            val intent = Intent(this, CropImageActivity::class.java)
            intent.putExtra("SourceUri", uri)
            startActivity(intent)
        }
    }

    companion object {
        const val IMAGE_URL = "IMAGE_URL"
        fun open(activity: Activity, url: String) {
            val intent = Intent(activity, ImageFullScreenActivity::class.java)
            intent.putExtra(IMAGE_URL, url)
            activity.startActivity(intent)
        }
    }
}