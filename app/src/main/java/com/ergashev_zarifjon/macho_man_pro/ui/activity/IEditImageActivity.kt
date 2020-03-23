package com.ergashev_zarifjon.macho_man_pro.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import androidx.fragment.app.Fragment
import ja.burhanrashid52.photoeditor.PhotoEditor
import java.io.File

interface IEditImageActivity {

    fun makeFullScreen()

    fun showProgress()

    fun hideProgress()

    fun saveTempImage(bitmap: Bitmap, doneFunction: (String?) -> Unit)

    fun saveTempImage(tempName: String, bitmap: Bitmap, doneFunction: (String?) -> Unit)

    fun replaceFragment(containerView: Int, fragment: Fragment)

    fun addFragment(containerView: Int, fragment: Fragment)

    fun replaceFragmentBackStack(containerView: Int, fragment: Fragment, tag: String? = null)

    fun addFragmentBackStack(containerView: Int, fragment: Fragment, tag: String? = null)

    fun showToast(message: Any)

    fun readTempImage(withProgress:Boolean=true,doneFunction: (Bitmap?) -> Unit)

    fun startaActivitys(intent: Intent)

    fun onBackPress()

    fun getTempFile(): File

    fun saveAsFile(imagePath: String, bitmap: Bitmap, onSaveListener: PhotoEditor.OnSaveListener)

    fun requestPermission(writeExternalStorage: String): Boolean

    fun checkPermissionAndRequest(doneFunc: () -> Unit, fail: () -> Unit, vararg list: String)

    fun shareOther(file: File)

    fun shareInstagram(mFileImagePath: File)

    fun shareFacebook(mFileImagePath: File)

    fun shareTwitter(mFileImagePath: File)

}