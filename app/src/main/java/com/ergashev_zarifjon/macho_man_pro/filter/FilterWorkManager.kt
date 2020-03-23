package com.ergashev_zarifjon.macho_man_pro.filter

import android.content.Context
import android.graphics.Bitmap
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ergashev_zarifjon.macho_man_pro.ServiceLocator
import com.ergashev_zarifjon.macho_man_pro.commons.*
import com.ergashev_zarifjon.macho_man_pro.data.entity.FilterItemMenu
import com.ergashev_zarifjon.macho_man_pro.image_filter.ImageFilter
import com.ergashev_zarifjon.macho_man_pro.model.EditTypeList
import com.ergashev_zarifjon.macho_man_pro.model.EditTypeList.Companion.getSubEditTypeList
import ja.burhanrashid52.photoeditor.PhotoEditorView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File

class FilterWorkManager(context: Context, para: WorkerParameters) : CoroutineWorker(context, para) {
    companion object {
        val FAIL_MESSAGE = "fail_message"
    }

    init {
        System.loadLibrary("NativeImageProcessor")
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val imageUrl = inputData.getString(FILTER_WORKER_IMAGE_URL)
            val originalImage: Bitmap = Util.readImageFromFile(imageUrl!!)
            val resizeBitmap = originalImage.resizeByMaximum(400)
            val filterMenuRepository =
                ServiceLocator.provideFilterMenuRepository(applicationContext)

            filterMenuRepository.deleteAllFilterItemMenu()
            filterMenuRepository.deleteAllFilterMenu()

            getSubEditTypeList(EditTypeList.BLUR, applicationContext).forEach { type ->
                // val sha="${type.type_name}${imageUrl}"
                val filterImage = filtered(type.type_code, resizeBitmap)

                val byteStream = ByteArrayOutputStream()
                filterImage?.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
                val byteArray = byteStream.toByteArray()
                filterImage?.recycle()

                val sha = SHaUtil.calcSHA(byteArray)
                val filterImagePath = File(FileUtil.images(), "$sha${FileUtil.IMAGE_TMP}")

                if (filterImage != null) {
                    Util.saveImageToFile(filterImagePath, byteArray)
                }
                filterMenuRepository.saveAllFilterItemMenu(
                    FilterItemMenu(
                        0,
                        EditTypeList.BLUR,
                        type.type_code,
                        type.type_name,
                        filterImagePath.path
                    )
                )
                println("ok ${type.type_code}")
            }

            val photoEditor = PhotoEditorView(applicationContext)
            val resizeBitmap2 = originalImage.resizeByMaximum(400)
            photoEditor.source.setImageBitmap(resizeBitmap2)


         /*    val thumbnailItemList= mutableListOf<ThumbnailItem>()

            ThumbnailsManager.clearThumbs()

            thumbnailItemList.clear()

            // add normal bitmap first
            val thumbnailItem = ThumbnailItem()
            thumbnailItem.image = resizeBitmap
            thumbnailItem.filterName = applicationContext.getString(R.string.filter_normal)
            ThumbnailsManager.addThumb(thumbnailItem)

            val filters = FilterPack.getFilterPack(applicationContext)

            for (filter in filters) {
                val tI = ThumbnailItem()
                tI.image = resizeBitmap
                tI.filter = filter
                tI.filterName = filter.name
                ThumbnailsManager.addThumb(tI)
            }

            thumbnailItemList.addAll(ThumbnailsManager.processThumbs(applicationContext))

            thumbnailItemList.forEach {filter->
                // applying the selected filter
                val filteredImage = resizeBitmap.copy(Bitmap.Config.ARGB_8888, true)
                // preview filtered image
               filter.filter.processFilter(filteredImage)

                val finalImage = filteredImage.copy(Bitmap.Config.ARGB_8888, true)

                val byteStream = ByteArrayOutputStream()
                finalImage.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
                val byteArray = byteStream.toByteArray()
                finalImage.recycle()
                val sha = SHaUtil.calcSHA(byteArray)
                val filterImagePath = File(FileUtil.images(), "$sha${FileUtil.IMAGE_TMP}")
                runBlocking(Dispatchers.IO) {
                    Util.saveImageToFile(filterImagePath, byteArray)
                    filterMenuRepository.saveAllFilterItemMenu(
                        FilterItemMenu(
                            0,
                            EditTypeList.FILTERS,
                            filter.filterName,
                            filter.filterName,
                            filterImagePath.path
                        )
                    )
                }


            }*/
/*
            EditTypeList.FILTER_PHOTO_EDITOR_ITEMS.keys.forEach { key ->
                val filter = EditTypeList.FILTER_PHOTO_EDITOR_ITEMS[key]
                val name = filter?.first as String

                val filterType = filter.second
                val code = filterType.name

                val editor = PhotoEditor.Builder(applicationContext, photoEditor).build()

                editor.setFilterEffect(filterType)
                editor.saveAsBitmap(object : OnSaveBitmap {
                    override fun onBitmapReady(saveBitmap: Bitmap) {
                        val byteStream = ByteArrayOutputStream()
                        saveBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
                        val byteArray = byteStream.toByteArray()
                        saveBitmap.recycle()
                        val sha = SHaUtil.calcSHA(byteArray)
                        val filterImagePath = File(FileUtil.images(), "$sha${FileUtil.IMAGE_TMP}")
                        runBlocking(Dispatchers.IO) {
                            Util.saveImageToFile(filterImagePath, byteArray)
                            filterMenuRepository.saveAllFilterItemMenu(
                                FilterItemMenu(
                                    0,
                                    EditTypeList.FILTERS,
                                    code,
                                    name,
                                    filterImagePath.path
                                )
                            )
                        }

                    }

                    override fun onFailure(e: java.lang.Exception?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })

            }

*/
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private suspend fun filtered(type_code: String, originalBitmap: Bitmap?): Bitmap? {
        return when (type_code) {
            EditTypeList.BLUR_1 -> originalBitmap?.let { ImageFilter.filter1(it) }
            EditTypeList.BLUR_2 -> originalBitmap?.let { ImageFilter.filter9(it) }
            EditTypeList.BLUR_3 -> originalBitmap?.let { ImageFilter.filter6(it) }
            EditTypeList.BLUR_4 -> originalBitmap?.let { ImageFilter.filter7(it) }
            //EditTypeList.BLUR_5 -> originalBitmap?.let { ImageFilter.filter5(it) }
            EditTypeList.BLUR_6 -> originalBitmap?.let { ImageFilter.filter8(it) }
            EditTypeList.BLUR_7 -> originalBitmap?.let { ImageFilter.filter2(it) }
            EditTypeList.BLUR_8 -> originalBitmap?.let { ImageFilter.filter3(it) }
            EditTypeList.BLUR_9 -> originalBitmap?.let { ImageFilter.filter4(it) }
            EditTypeList.BLUR_10 -> originalBitmap?.let { ImageFilter.filter5(it) }
            else -> null
        }
    }

}