package com.ergashev_zarifjon.macho_man_pro.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.commons.showMessageDialog
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.FirstFragment
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.main_fragment.MainFragment
import java.io.File

class MainActivity : BaseActivity(), IMainActivity {
    override fun startActivitys(intet: Intent) {
        startActivity(intent)
    }

    private val PERMISSION_REQUEST = 1111
    private var permissionGrantedAction: (() -> Unit)? = null
    private var permissionFailed: (() -> Unit)? = null
    private var permissionsList: Array<out String>? = emptyArray()

    override fun checkPermission(function: () -> Unit, fail: () -> Unit, vararg list: String) {
        if (list.isNotEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                list.forEach {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            it
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        fail.invoke()
                        return
                    }
                }
                function.invoke()
            } else {
                function.invoke()

            }
        }
    }

    override fun checkPermissionAndRequest(
        function: () -> Unit,
        fail: () -> Unit,
        vararg list: String
    ) {
        if (list.isNotEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                list.forEach {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            it
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        permissionGrantedAction = function
                        permissionFailed = fail
                        permissionsList = list
                        requestPermissions(list, PERMISSION_REQUEST)
                        return
                    }
                }
                function.invoke()
            } else {
                function.invoke()

            }
        }
    }

    private val PERMISSION_GALLERY = 1006
    private val PERMISSION_CAMERA = 108

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            addFragment(R.id.fragment_container, FirstFragment())
        } else
            replaceFragment(R.id.fragment_container, FirstFragment())

    }

    override fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun back() {
        replaceFragment(R.id.fragment_container, MainFragment())
    }

    override fun onBackPressed() {
        if (isMainFragment())
            super.onBackPressed()

    }

    override fun openCamera() {
        checkPermissionAndRequest({
            val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (camera.resolveActivity(packageManager) != null) {
                startActivityForResult(camera, PERMISSION_CAMERA)
            } else {
                showToast("Camera app not found")
            }

        }, {
            showMessageDialog(R.string.warning, R.string.you_need_all_permission)
        }, Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)


    }

    private fun isMainFragment(): Boolean {
        val fragments = supportFragmentManager.fragments
        for (f in fragments) {
            if (f != null && f is FirstFragment) {
                f.onBackPressed()
            }
        }
        return false
    }

    override fun choseFile() {
        checkPermissionAndRequest({
            val intent = Intent()
            intent.type = "*/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, getString(R.string.select_picture)),
                PERMISSION_GALLERY
            )

        }, {
            showMessageDialog(R.string.warning, R.string.you_need_all_permission)
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PERMISSION_CAMERA -> {
                    bitmap = data?.extras!!.get("data") as Bitmap
                    bitmap?.let { bitmap ->
                        saveTempImage(bitmap) { path ->
                            val intent = Intent(this, CropImageActivity::class.java)
                            intent.putExtra("SourceUri", Uri.fromFile(File(path)))
                            startActivity(intent)
                        }
                    }
                }
                PERMISSION_GALLERY -> {
                    val uri = data?.data
                    val intent = Intent(this, CropImageActivity::class.java)
                    intent.putExtra("SourceUri", uri)
                    startActivity(intent)


                }
            }
        }
    }
    /*
    *
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PERMISSION_CAMERA -> {
                    bitmap = data?.extras!!.get("data") as Bitmap
                    bitmap?.let { bitmap ->
                        saveTempImage(bitmap) { path ->
                            val intent = Intent(this, EditImageActivity::class.java)
                            intent.putExtra("PICK_RESULT", "SHERBAEV")
                            intent.putExtra("IMAGEURL", path)
                            startActivity(intent)
                        }
                    }
                }
                PERMISSION_GALLERY -> {
                    val uri = data?.data
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    bitmap?.let { bitmap ->
                        saveTempImage(bitmap) { path ->
                            val intent = Intent(this, EditImageActivity::class.java)
                            intent.putExtra("PICK_RESULT", "SHERBAEV")
                            intent.putExtra("IMAGEURL", path)
                            startActivity(intent)
                        }
                    }
                }
            }
        }

    }*/

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST ->
                if ((grantResults.isNotEmpty()
                            && permissionsList != null
                            && grantResults.size == permissionsList!!.size
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    permissionGrantedAction?.invoke()
                } else {
                    permissionFailed?.invoke()
                }
        }

    }
}
