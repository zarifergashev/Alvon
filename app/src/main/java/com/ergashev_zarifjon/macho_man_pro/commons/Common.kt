package com.ergashev_zarifjon.macho_man_pro.commons

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DecimalFormat
import kotlin.math.roundToInt


suspend fun Context.saveTempFile(image: Bitmap): String {
    val cw = ContextWrapper(this)
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
    return directory.absolutePath
}

fun Context.isOrentationLand(): Boolean {
    val orintation = this.resources.configuration.orientation
    return return orintation == Configuration.ORIENTATION_LANDSCAPE
}

fun Any.getString(context: Context): String = when (this) {
    is String -> this
    is CharSequence -> this.toString()
    is Int -> context.resources.getString(this)
    else -> throw Exception("text type not found")
}

fun Context.showMessageDialog(
    title: Any,
    message: Any,
    negativBtnText: Any? = "",
    negativBtnAction: (() -> Unit)? = null,
    positiveBtnText: Any? = "",
    positiveBtnAction: (() -> Unit)? = null
) {
    val builder = AlertDialog.Builder(this)
        .setTitle(title.getString(this))
        .setMessage(message.getString(this))

    if (negativBtnText != null)
        builder.setNegativeButton(
            negativBtnText.getString(this)
        ) { p0, p1 ->
            negativBtnAction?.invoke()
        }
    if (positiveBtnText != null)
        builder.setPositiveButton(
            positiveBtnText.getString(this)
        ) { p0, p1 ->
            positiveBtnAction?.invoke()
        }

    builder.show()

}

fun Long.getFileSizeKbOrMb(): String {
    val format = DecimalFormat("00.0")
    val quant = this / 1024.0
    return if (quant > 1) {
        "${format.format(quant)} MB"
    } else {
        "${format.format(quant)} KB"
    }

}

fun File.getFileSizeKbOrMb(): String {
    val format = DecimalFormat("00.0")
    val quant = this.length() / (1024.0 * 1024)
    return if (quant > 1) {
        "${format.format(quant)} MB"
    } else {
        "${format.format(quant)} KB"
    }

}

fun Bitmap.flip(): Bitmap {
    val matrix = Matrix().apply { postScale(-1f, 1f, width / 2f, width / 2f) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Context.changeDrawableColor(@DrawableRes resId: Int, @ColorRes resColorId: Int): Drawable? {
    if (resId != 0 && resColorId != 0) {
        val res = this.resources
        val drawable = res.getDrawable(resId).constantState!!.newDrawable().mutate()
        drawable.setColorFilter(res.getColor(resColorId), PorterDuff.Mode.SRC_IN)
        return drawable
    } else {
        return null
    }
}

fun Bitmap.resizeByMaximum(size: Int): Bitmap {
    val width = this.width
    val height = this.height
    return if (width > height) {
        resizeByWitdh(size)
    } else {
        resizeByHeight(size)
    }
}

fun Bitmap.resizeByWitdh(size: Int): Bitmap {
    if (size == 0) throw  RuntimeException("size=0")
    val width = this.width
    val height = this.height
    if (width < size) return this
    val resizePercent = 100 - ((size * 100) / width)
    val h = (height - ((height / 100) * resizePercent)).toDouble().roundToInt()
    return Bitmap.createScaledBitmap(this, size, h, true)
}

fun Bitmap.resizeByHeight(size: Int): Bitmap {
    if (size == 0) throw  RuntimeException("size=0")
    val width = this.width
    val height = this.height
    if (height < size) return this
    val resizePercent = 100 - ((size * 100) / height)
    val w = (width - ((width / 100) * resizePercent)).toDouble().roundToInt()
    return Bitmap.createScaledBitmap(this, size, w, true)
}

fun Context.getText(text: Any): CharSequence? {
    return when (text) {
        is Int -> this.getString(text)
        is String -> text
        is CharSequence -> text
        else -> null
    }
}


