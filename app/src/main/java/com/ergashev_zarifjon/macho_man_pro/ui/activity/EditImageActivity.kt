package com.ergashev_zarifjon.macho_man_pro.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.ergashev_zarifjon.macho_man_pro.commons.GenericFileProvider
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.commons.FILTER_WORKER_IMAGE_URL
import com.ergashev_zarifjon.macho_man_pro.filter.FilterWorkManager
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.edit_image.EIMainFragmanet
import ja.burhanrashid52.photoeditor.OnPhotoEditorListener
import ja.burhanrashid52.photoeditor.ViewType
import java.io.File


class EditImageActivity : BaseActivity(), OnPhotoEditorListener, IEditImageActivity
    , View.OnClickListener {

    private val PERMISSION_REQUEST = 1111
    override fun shareOther(file: File) {
        val uri = GenericFileProvider.getUriForFile(this, "com.ergashev_zarifjon.macho_man_pro", file)
        val intentShareFile = Intent(Intent.ACTION_SEND)
        intentShareFile.putExtra(
            Intent.EXTRA_TEXT,
            "Hey please check this application https://play.google.com/store/apps/details?id=$packageName}"
        )
        intentShareFile.putExtra(Intent.EXTRA_STREAM, uri)
        intentShareFile.type = "image/*"
        intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(intentShareFile, "Share File"))
    }

    val READ_WRITE_STORAGE = 52

    private var permissionGrantedAction: (() -> Unit)? = null
    private var permissionFailed: (() -> Unit)? = null
    private var permissionsList: Array<out String>? = emptyArray()

    override fun checkPermissionAndRequest(function: () -> Unit, fail: () -> Unit, vararg list: String) {
        if (list.isNotEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                list.forEach {
                    if (ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED) {
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


    override fun requestPermission(permission: String): Boolean {
        val isGranted = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        if (!isGranted) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), READ_WRITE_STORAGE)
        }
        return isGranted
    }


    override fun onBackPress() {
        onBackPressed()
    }

    override fun startaActivitys(intent: Intent) {
        startActivity(intent)
    }

    override fun onRemoveViewListener(numberOfAddedViews: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var imageUrl: String? = null
    override fun readTempImage(withProgress:Boolean
                               ,doneFunction: (Bitmap?) -> Unit) {
        imageUrl?.let { readTempImage(it,withProgress, doneFunction) }
    }
/*

    private var mPhotoEditor: PhotoEditor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeFullScreen()
        setContentView(R.layout.edit_image_activty)
        if (intent != null) {
            val imageSrc = intent.getIntExtra("IMAGESRC", -1)
            val imageUrl = intent.getStringExtra("IMAGEURL")
            val command = intent.getStringExtra("PICK_RESULT")
            if (command != null && command == ("SHERBAEV")) {
                readTempImage(imageUrl) {
                    Glide.with(this)
                        .load(it)
                        .into(photoEditorView.source)
                }
            } else if (imageSrc == -1) {
                Glide.with(this)
                    .load(imageUrl)
                    .into(photoEditorView.source)

            } else {
                Glide.with(this)
                    .load(imageSrc)
                    .into(photoEditorView.source)
            }


        }
        initView()
    }

    private fun initView() {
        mPhotoEditor = PhotoEditor.Builder(this, photoEditorView)
            .setPinchTextScalable(true) // set flag to make text scalable when pinch
            .build() // build photo editor sdk
        mPhotoEditor?.setFilterEffect(PhotoFilter.BRIGHTNESS)
        //mPhotoEditor?.brushEraser()
        // mPhotoEditor?.add

        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.img)
        mPhotoEditor?.addImage(largeIcon)

        mPhotoEditor?.setOnPhotoEditorListener(this);

    }
*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_image_activty)
        if (savedInstanceState == null) {
            addFragment(R.id.ei_container, EIMainFragmanet())
        } else
            replaceFragment(R.id.ei_container, EIMainFragmanet())
        if (intent != null) {
            imageUrl = intent.getStringExtra("IMAGEURL")
            if (imageUrl == null) {
                showToast(getString(R.string.image_not_found))
                finish()
            }
            val command = intent.getStringExtra("PICK_RESULT")
            if (command != null && command == ("ERGASHEV")) {

            }
        }
        initFilterWorker()
    }

    private fun initFilterWorker() {
        val workResult=OneTimeWorkRequest.Builder(FilterWorkManager::class.java)
            .setInputData(createFilterWorkerData())
            .build()
        val workManager=WorkManager.getInstance(this)
            workManager.enqueue(workResult)
    }

    private fun createFilterWorkerData(): Data {
        return Data.Builder()
            .putString(FILTER_WORKER_IMAGE_URL,imageUrl)
            .build()
    }

    override fun onClick(v: View?) {
    }

    override fun onEditTextChangeListener(rootView: View?, text: String?, colorCode: Int) {
    }

    override fun onStartViewChangeListener(viewType: ViewType?) {
    }

    override fun onRemoveViewListener(viewType: ViewType?, numberOfAddedViews: Int) {
    }

    override fun onAddViewListener(viewType: ViewType?, numberOfAddedViews: Int) {
    }

    override fun onStopViewChangeListener(viewType: ViewType?) {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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

