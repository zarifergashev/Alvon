package com.ergashev_zarifjon.macho_man_pro.commons

import android.os.Environment
import java.io.File

object FileUtil {

    const val MY_FILE_PATH = "macho_man_pro"
    const val IMAGES_PATH = "imges"
    const val IMAGE_TMP = ".tmp"

    fun appPath() = File(Environment.getExternalStorageDirectory(), MY_FILE_PATH).also { if (!it.exists()) it.mkdir() }.absolutePath

    fun images() = File(appPath(), IMAGES_PATH).also { if (!it.exists()) it.mkdir() }.absoluteFile

    fun deleteFolderResource(file: File):Boolean{
        if (file.isDirectory)
        {
            file.listFiles().forEach {
                if (!deleteFolderResource(it))
                    return false
            }

        }
        return file.delete()
    }


    fun isImageExist(sha: String) = File("${images()}/$sha$IMAGE_TMP").exists()

}
