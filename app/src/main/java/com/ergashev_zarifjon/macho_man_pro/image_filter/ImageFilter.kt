package com.ergashev_zarifjon.macho_man_pro.image_filter

import android.graphics.Bitmap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ImageFilter {

    suspend fun filter1(bitmap: Bitmap, dispatcher: CoroutineDispatcher = Dispatchers.IO): Bitmap =
        withContext(dispatcher) {
            return@withContext Pixelate.fromBitmap(
                bitmap,
                PixelateLayer.Builder(PixelateLayer.Shape.Diamond)
                    .setResolution(48f)
                    .setSize(50f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Diamond)
                    .setResolution(48f)
                    .setOffset(24f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Circle)
                    .setResolution(8f)
                    .setSize(6f)
                    .build()
            )
        }

    suspend fun filter2(bitmap: Bitmap, dispatcher: CoroutineDispatcher = Dispatchers.IO): Bitmap =
        withContext(dispatcher) {
            return@withContext Pixelate.fromBitmap(
                bitmap,
                PixelateLayer.Builder(PixelateLayer.Shape.Square)
                    .setResolution(32f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Circle)
                    .setResolution(32f)
                    .setOffset(15f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Circle)
                    .setResolution(32f)
                    .setSize(26f)
                    .setOffset(13f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Circle)
                    .setResolution(32f)
                    .setSize(18f)
                    .setOffset(10f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Circle)
                    .setResolution(32f)
                    .setSize(12f)
                    .setOffset(8f)
                    .build()
            )
        }

    suspend fun filter3(bitmap: Bitmap, dispatcher: CoroutineDispatcher = Dispatchers.IO): Bitmap =
        withContext(dispatcher) {
            return@withContext Pixelate.fromBitmap(
                bitmap,
                PixelateLayer.Builder(PixelateLayer.Shape.Square)
                    .setResolution(48f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Diamond)
                    .setResolution(48f)
                    .setOffset(12f)
                    .setAlpha(0.5f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Diamond)
                    .setResolution(48f)
                    .setOffset(36f)
                    .setAlpha(0.5f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Circle)
                    .setResolution(16f)
                    .setSize(8f)
                    .setOffset(4f)
                    .build()
            )
        }

    suspend fun filter4(bitmap: Bitmap, dispatcher: CoroutineDispatcher = Dispatchers.IO): Bitmap =
        withContext(dispatcher) {
            return@withContext Pixelate.fromBitmap(
                bitmap,
                PixelateLayer.Builder(PixelateLayer.Shape.Circle)
                    .setResolution(32f)
                    .setSize(6f)
                    .setOffset(8f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Circle)
                    .setResolution(32f)
                    .setSize(9f)
                    .setOffset(16f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Circle)
                    .setResolution(32f)
                    .setSize(12f)
                    .setOffset(24f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Circle)
                    .setResolution(32f)
                    .setSize(9f)
                    .setOffset(0f)
                    .build()
            )
        }

    suspend fun filter5(bitmap: Bitmap, dispatcher: CoroutineDispatcher = Dispatchers.IO): Bitmap =
        withContext(dispatcher) {
            return@withContext Pixelate.fromBitmap(
                bitmap,
                PixelateLayer.Builder(PixelateLayer.Shape.Diamond)
                    .setResolution(24f)
                    .setSize(25f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Diamond)
                    .setResolution(24f)
                    .setOffset(12f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Square)
                    .setResolution(24f)
                    .setAlpha(0.6f)
                    .build()
            )
        }

    suspend fun filter6(bitmap: Bitmap, dispatcher: CoroutineDispatcher = Dispatchers.IO): Bitmap =
        withContext(dispatcher) {
            return@withContext Pixelate.fromBitmap(
                bitmap,
                PixelateLayer.Builder(PixelateLayer.Shape.Square)
                    .setResolution(32f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Circle)
                    .setResolution(32f)
                    .setOffset(16f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Circle)
                    .setResolution(32f)
                    .setOffset(0f)
                    .setAlpha(0.5f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Circle)
                    .setResolution(16f)
                    .setSize(9f)
                    .setOffset(0f)
                    .setAlpha(0.5f)
                    .build()
            )
        }

    suspend fun filter7(bitmap: Bitmap, dispatcher: CoroutineDispatcher = Dispatchers.IO): Bitmap =
        withContext(dispatcher) {
            return@withContext Pixelate.fromBitmap(
                bitmap,
                PixelateLayer.Builder(PixelateLayer.Shape.Circle)
                    .setResolution(24f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Circle)
                    .setResolution(24f)
                    .setSize(9f)
                    .setOffset(12f)
                    .build()
            )
        }

    suspend fun filter8(bitmap: Bitmap, dispatcher: CoroutineDispatcher = Dispatchers.IO): Bitmap =
        withContext(dispatcher) {
            return@withContext Pixelate.fromBitmap(
                bitmap,
                PixelateLayer.Builder(PixelateLayer.Shape.Square)
                    .setResolution(48f)
                    .setOffset(24f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Circle)
                    .setResolution(48f)
                    .setOffset(0f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Diamond)
                    .setResolution(16f)
                    .setSize(15f)
                    .setOffset(0f)
                    .setAlpha(0.6f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Diamond)
                    .setResolution(16f)
                    .setSize(15f)
                    .setOffset(8f)
                    .setAlpha(0.6f)
                    .build()
            )
        }

    suspend fun filter9(bitmap: Bitmap, dispatcher: CoroutineDispatcher = Dispatchers.IO): Bitmap =
        withContext(dispatcher) {
            return@withContext Pixelate.fromBitmap(
                bitmap,
                PixelateLayer.Builder(PixelateLayer.Shape.Square)
                    .setResolution(48f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Diamond)
                    .setResolution(12f)
                    .setSize(8f)
                    .build(),
                PixelateLayer.Builder(PixelateLayer.Shape.Diamond)
                    .setResolution(12f)
                    .setSize(8f)
                    .setOffset(6f)
                    .build()
            )
        }

}