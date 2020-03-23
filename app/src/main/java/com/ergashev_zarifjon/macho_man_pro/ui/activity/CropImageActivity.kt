package com.ergashev_zarifjon.macho_man_pro.ui.activity

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.RectF
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.core.content.ContextCompat
import com.ergashev_zarifjon.macho_man_pro.commons.Command
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.commons.showMessageDialog
import com.ergashev_zarifjon.macho_man_pro.commons.MyDialog
import com.isseiaoki.simplecropview.CropImageView
import com.isseiaoki.simplecropview.callback.CropCallback
import com.isseiaoki.simplecropview.callback.LoadCallback
import com.isseiaoki.simplecropview.util.Logger
import kotlinx.android.synthetic.main.fragment_basic.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class CropImageActivity : BaseActivity() {
    private var mCropView: CropImageView? = null
    private val mCompressFormat = Bitmap.CompressFormat.PNG
    private var mFrameRect: RectF? = null
    private var mSourceUri: Uri? = null
    private val REQUEST_PICK_IMAGE = 10011
    private val REQUEST_SAF_PICK_IMAGE = 10012
    private val PROGRESS_DIALOG = "ProgressDialog"
    private val KEY_FRAME_RECT = "FrameRect"
    private val KEY_SOURCE_URI = "SourceUri"

    private val mLoadCallback = object : LoadCallback {
        override fun onSuccess() {}

        override fun onError(e: Throwable) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_basic)
        if (savedInstanceState != null) {
            // restore data
            mFrameRect = savedInstanceState.getParcelable(KEY_FRAME_RECT)
            mSourceUri = savedInstanceState.getParcelable(KEY_SOURCE_URI)
        }
        if (intent != null) {
            mSourceUri = intent.getParcelableExtra("SourceUri")
        }
        if (mSourceUri == null) {
            showToast(R.string.not_image)
        }
        initViews()
    }

    private fun initViews() {
        bindViews()
        // load image
        mCropView?.load(mSourceUri)
            ?.initialFrameRect(mFrameRect)
            ?.useThumbnail(true)
            ?.execute(mLoadCallback)

    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // save data
        outState.putParcelable(KEY_FRAME_RECT, mCropView?.actualCropRect)
        outState.putParcelable(KEY_SOURCE_URI, mCropView?.sourceUri)
    }

    private val btnListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.buttonDone -> cropImage()
            R.id.buttonFitImage -> mCropView?.setCropMode(CropImageView.CropMode.FIT_IMAGE)
            R.id.button1_1 -> mCropView?.setCropMode(CropImageView.CropMode.SQUARE)
            R.id.button3_4 -> mCropView?.setCropMode(CropImageView.CropMode.RATIO_3_4)
            R.id.button4_3 -> mCropView?.setCropMode(CropImageView.CropMode.RATIO_4_3)
            R.id.button9_16 -> mCropView?.setCropMode(CropImageView.CropMode.RATIO_9_16)
            R.id.button16_9 -> mCropView?.setCropMode(CropImageView.CropMode.RATIO_16_9)
            R.id.buttonCustom -> mCropView?.setCustomRatio(7, 5)
            R.id.buttonFree -> mCropView?.setCropMode(CropImageView.CropMode.FREE)
            R.id.buttonCircle -> mCropView?.setCropMode(CropImageView.CropMode.CIRCLE)
            R.id.buttonShowCircleButCropAsSquare -> mCropView?.setCropMode(CropImageView.CropMode.CIRCLE_SQUARE)
            R.id.buttonRotateLeft -> mCropView?.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D)
            R.id.buttonRotateRight -> mCropView?.rotateImage(CropImageView.RotateDegrees.ROTATE_90D)
            R.id.buttonPickImage -> {
                MyDialog.Builder()
                    .title(R.string.select_picture)
                    .option(R.drawable.ic_gallery, -1, R.string.gallery,
                        Command { choseFile() }).option(R.drawable.ic_camera, -1, R.string.camera,
                        Command { openCamera() }).show(this)
            }
        }
    }

    private val mCropCallback = object : CropCallback {
        override fun onSuccess(cropped: Bitmap) {
            saveTempImage(cropped) { path ->
                hideProgress()
                val intent = Intent(this@CropImageActivity, EditImageActivity::class.java)
                intent.putExtra("PICK_RESULT", "ERGASHEV")
                intent.putExtra("IMAGEURL", path)
                startActivity(intent)
                finish()
            }
        }

        override fun onError(e: Throwable) {
            hideProgress()
            e.printStackTrace()
        }
    }

    fun cropImage() {
        showProgress()
        mCropView?.crop(mSourceUri)?.execute(mCropCallback)
    }

    private fun bindViews() {
        mCropView = findViewById(R.id.cropImageView)
        buttonDone.setOnClickListener(btnListener)
        buttonFitImage.setOnClickListener(btnListener)
        button1_1.setOnClickListener(btnListener)
        button3_4.setOnClickListener(btnListener)
        button4_3.setOnClickListener(btnListener)
        button9_16.setOnClickListener(btnListener)
        button16_9.setOnClickListener(btnListener)
        buttonFree.setOnClickListener(btnListener)
        buttonPickImage.setOnClickListener(btnListener)
        buttonRotateLeft.setOnClickListener(btnListener)
        buttonRotateRight.setOnClickListener(btnListener)
        buttonCustom.setOnClickListener(btnListener)
        buttonCircle.setOnClickListener(btnListener)
        buttonShowCircleButCropAsSquare.setOnClickListener(btnListener)
    }

    fun choseFile() {
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

    fun openCamera() {
        checkPermissionAndRequest({
            val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (camera.resolveActivity(packageManager) != null) {
                startActivityForResult(camera, PERMISSION_CAMERA)
            } else {
                showToast("Camera app not found")
            }

        }, {
            showMessageDialog(R.string.warning, R.string.you_need_all_permission)
        }, Manifest.permission.CAMERA)


    }

    private val PERMISSION_REQUEST = 1111
    private var permissionGrantedAction: (() -> Unit)? = null
    private var permissionFailed: (() -> Unit)? = null
    private var permissionsList: Array<out String>? = emptyArray()
    fun checkPermissionAndRequest(function: () -> Unit, fail: () -> Unit, vararg list: String) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PERMISSION_CAMERA -> {
                    bitmap = data?.extras!!.get("data") as Bitmap
                    bitmap?.let { bitmap ->
                        saveTempImage(bitmap) { path ->
                            mSourceUri = Uri.fromFile(File(path))
                            initViews()
                        }
                    }
                }
                PERMISSION_GALLERY -> {
                    mSourceUri = data?.data
                    initViews()
                }
            }
        }
    }

    fun createNewUri(context: Context?, format: Bitmap.CompressFormat): Uri? {
        val currentTimeMillis = System.currentTimeMillis()
        val today = Date(currentTimeMillis)
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss")
        val title = dateFormat.format(today)
        val dirPath = getDirPath()
        val fileName = "scv" + title + "." + getMimeType(format)
        val path = "$dirPath/$fileName"
        val file = File(path)
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, title)
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/" + getMimeType(format))
        values.put(MediaStore.Images.Media.DATA, path)
        val time = currentTimeMillis / 1000
        values.put(MediaStore.MediaColumns.DATE_ADDED, time)
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, time)
        if (file.exists()) {
            values.put(MediaStore.Images.Media.SIZE, file.length())
        }

        val resolver = context!!.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        Logger.i("SaveUri = " + uri!!)
        return uri
    }

    fun getMimeType(format: Bitmap.CompressFormat): String {
        Logger.i("getMimeType CompressFormat = $format")
        when (format) {
            Bitmap.CompressFormat.JPEG -> return "jpeg"
            Bitmap.CompressFormat.PNG -> return "png"
        }
        return "png"
    }

    fun getDirPath(): String {
        var dirPath = ""
        var imageDir: File? = null
        val extStorageDir = Environment.getExternalStorageDirectory()
        if (extStorageDir.canWrite()) {
            imageDir = File(extStorageDir.path + "/simplecropview")
        }
        if (imageDir != null) {
            if (!imageDir.exists()) {
                imageDir.mkdirs()
            }
            if (imageDir.canWrite()) {
                dirPath = imageDir.path
            }
        }
        return dirPath
    }

    fun createTempUri(context: Context): Uri {
        return Uri.fromFile(File(context.cacheDir, "cropped"))
    }

    fun createSaveUri(): Uri? {
        return createNewUri(this, mCompressFormat)
    }
}