package com.ergashev_zarifjon.macho_man_pro.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ergashev_zarifjon.macho_man_pro.R
import com.ergashev_zarifjon.macho_man_pro.commons.showMessageDialog
import com.ergashev_zarifjon.macho_man_pro.ui.fragment.ProgresDialog
import ja.burhanrashid52.photoeditor.PhotoEditor
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


open class BaseActivity : AppCompatActivity() {
    private var saveDoneFunc: ((String?) -> Unit)? = null
    private var readDoneFunc: ((Bitmap?) -> Unit)? = null

    companion object {
        var bitmap: Bitmap? = null
    }

    fun makeFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private val progresDialog by lazy {
        ProgresDialog()
    }

    fun replaceFragmentBackStack(containerView: Int, fragment: Fragment, tag: String?) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(tag)
            .replace(containerView, fragment)
            .commit()
    }

    fun addFragmentBackStack(containerView: Int, fragment: Fragment, tag: String?) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(tag)
            .add(containerView, fragment)
            .commit()

    }

    fun replaceFragment(containerView: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(containerView, fragment)
            .commit()
    }

    fun addFragment(containerView: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(containerView, fragment)
            .commit()
    }

    fun showToast(messa: Any) {
        val message = when (messa) {
            is Int -> resources.getString(messa)
            is String -> messa
            is CharSequence -> messa
            else -> "Error"
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showProgress() {
        if (!progresDialog.isAdded )
            progresDialog.show(supportFragmentManager, "dialog")
    }

    fun getTempFile(): File {
        val cw = ContextWrapper(applicationContext)
        // path to /data/data/yourapp/app_data/imageDir
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        return File(directory, "temp.png")
    }

    fun hideProgress() {
        //  if (progresDialog.isAdded)
        progresDialog.dialog?.dismiss()
    }

    fun saveTempImage(bitmap: Bitmap, doneFunction: (String?) -> Unit) {
        this.saveDoneFunc = doneFunction
        SaveTask().execute(bitmap)
    }

    fun saveTempImage(tempName: String, bitmap: Bitmap, doneFunction: (String?) -> Unit) {
        this.saveDoneFunc = doneFunction
        SaveTaskByName().execute(Pair(first = tempName, second = bitmap))
    }

    fun readTempImage(imagePath: String, withProgress: Boolean, doneFunction: (Bitmap?) -> Unit) {
        this.readDoneFunc = doneFunction
        ReadImage(withProgress).execute(imagePath)
    }

    fun saveAsFile(
        imagePath: String,
        bitmap: Bitmap,
        onSaveListener: PhotoEditor.OnSaveListener
    ) {

        object : AsyncTask<String, String, Exception>() {

            @SuppressLint("MissingPermission")
            override fun doInBackground(vararg strings: String): Exception? {
                // Create a media file name
                val file = File(imagePath)
                try {
                    val out = FileOutputStream(file, false)
                    bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, out)

                    out.flush()
                    out.close()
                    return null
                } catch (e: Exception) {
                    e.printStackTrace()
                    return e
                }

            }

            override fun onPostExecute(e: Exception?) {
                super.onPostExecute(e)
                if (e == null) {
                    onSaveListener.onSuccess(imagePath)
                } else {
                    onSaveListener.onFailure(e)
                }
            }

        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    inner class SaveTask : AsyncTask<Bitmap, Void, String>() {
        override fun onPreExecute() {
            //  showProgress()
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: Bitmap?): String {
            val image = params[0] ?: return ""
            val cw = ContextWrapper(applicationContext)
            // path to /data/data/yourapp/app_data/imageDir
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            val mypath = File(directory, "temp.png")

            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(mypath)
                // Use the compress method on the BitMap object to write image to the OutputStream
                image.compress(Bitmap.CompressFormat.PNG, 100, fos)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    fos!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return mypath.absolutePath
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            //      hideProgress()
            saveDoneFunc?.invoke(result)
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class SaveTaskByName : AsyncTask<Pair<String, Bitmap>, Void, String>() {

        override fun onPreExecute() {
            //  showProgress()
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: Pair<String, Bitmap>?): String {
            val param = params[0] ?: return ""
            val image = param.second
            val imageName = param.first
            val cw = ContextWrapper(applicationContext)
            // path to /data/data/yourapp/app_data/imageDir
            val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
            val mypath = File(directory, "$imageName.png")
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(mypath)
                // Use the compress method on the BitMap object to write image to the OutputStream
                image.compress(Bitmap.CompressFormat.PNG, 100, fos)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    fos!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return mypath.absolutePath
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            //      hideProgress()
            saveDoneFunc?.invoke(result)
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class ReadImage(private val withProgress: Boolean) : AsyncTask<String, Void, Bitmap>() {
        override fun onPreExecute() {
            if (withProgress)
                showProgress()
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): Bitmap? {
            val path = params[0] ?: return null

            try {
                val bitmap: Bitmap?
                val f = File(path)
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                bitmap = BitmapFactory.decodeStream(FileInputStream(f), null, options)
                return bitmap
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            bitmap = result
            readDoneFunc?.invoke(result)
            hideProgress()
        }
    }

    private fun checkAppInstall(uri: String): Boolean {
        val pm = packageManager
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            //Error
        }

        return false
    }


    fun shareInstagram(mFileImagePath: File) {
        val intent = packageManager.getLaunchIntentForPackage("com.instagram.android")
        if (intent != null) {
            val mIntentShare = Intent(Intent.ACTION_SEND)
            val mStrExtension =
                android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(mFileImagePath).toString())
            val mStrMimeType =
                android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(mStrExtension)
            if (mStrExtension.equals("", ignoreCase = true) || mStrMimeType == null) {
                // if there is no extension or there is no definite mimetype, still try to open the file
                mIntentShare.type = "text*//*"
            } else {
                mIntentShare.type = mStrMimeType
            }
            mIntentShare.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(mFileImagePath))
            mIntentShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            mIntentShare.setPackage("com.instagram.android")
            startActivity(mIntentShare)
        } else {
            this.showMessageDialog(R.string.error, "Instagram have not been installed.")
        }
    }

    fun shareFacebook(mFileImagePath: File) {
        val intent = packageManager.getLaunchIntentForPackage("com.facebook.katana")
        if (intent != null) {
            val mIntentShare = Intent(Intent.ACTION_SEND)
            val mStrExtension =
                android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(mFileImagePath).toString())
            val mStrMimeType =
                android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(mStrExtension)
            if (mStrExtension.equals("", ignoreCase = true) || mStrMimeType == null) {
                // if there is no extension or there is no definite mimetype, still try to open the file
                mIntentShare.type = "text*//*"
            } else {
                mIntentShare.type = mStrMimeType
            }
            mIntentShare.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(mFileImagePath))
            mIntentShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            mIntentShare.setPackage("com.facebook.katana")
            startActivity(mIntentShare)
        } else {
            this.showMessageDialog(R.string.error, "Facebook have not been installed.")
        }
    }

    fun shareTwitter(mFileImagePath: File) {
        val intent = packageManager.getLaunchIntentForPackage("com.twitter.android")
        if (intent != null) {
            val mIntentShare = Intent(Intent.ACTION_SEND)
            val mStrExtension =
                android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(mFileImagePath).toString())
            val mStrMimeType =
                android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(mStrExtension)
            if (mStrExtension.equals("", ignoreCase = true) || mStrMimeType == null) {
                // if there is no extension or there is no definite mimetype, still try to open the file
                mIntentShare.type = "text*//*"
            } else {
                mIntentShare.type = mStrMimeType
            }
            mIntentShare.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(mFileImagePath))
            mIntentShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            mIntentShare.setPackage("com.twitter.android")
            startActivity(mIntentShare)
        } else {
            this.showMessageDialog(R.string.error, "Twitter have not been installed.")
        }
    }
}