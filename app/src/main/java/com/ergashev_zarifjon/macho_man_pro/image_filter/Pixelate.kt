/*
 * Close Pixelate for Android
 * based on http://desandro.com/resources/close-pixelate/
 *
 * Developed by
 * - David DeSandro  http://desandro.com
 * - John Schulz  http://twitter.com/jfsiii
 *
 * Android port by
 * - Boris Maslakov
 *
 * Licensed under MIT license
 */

package com.ergashev_zarifjon.macho_man_pro.image_filter

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

import java.io.IOException
import java.io.InputStream
import java.util.HashMap
import kotlin.math.sqrt

object Pixelate {
    private val SQRT2 = sqrt(2.0).toFloat()

    @Throws(IOException::class)
 suspend   fun fromAsset(assetManager: AssetManager, path: String, vararg layers: PixelateLayer): Bitmap {
        return fromInputStream(assetManager.open(path), *layers)
    }

    @Throws(IOException::class)
  suspend  fun fromInputStream(inputStream: InputStream, vararg layers: PixelateLayer): Bitmap {
        val `in` = BitmapFactory.decodeStream(inputStream)
        inputStream.close()
        val out = fromBitmap(`in`, *layers)
        `in`.recycle()
        return out
    }

    suspend fun fromBitmap(`in`: Bitmap, vararg layers: PixelateLayer): Bitmap {
        val out = Bitmap.createBitmap(`in`.width, `in`.height, Bitmap.Config.ARGB_8888)
        render(`in`, out, *layers)
        return out
    }

  suspend  fun render(`in`: Bitmap, out: Bitmap, vararg layers: PixelateLayer) {
        render(`in`, null, out, *layers)
    }

   suspend fun render(`in`: Bitmap, inBounds: Rect?, out: Bitmap, vararg layers: PixelateLayer) {
        render(`in`, inBounds, out, null, *layers)
    }

    suspend fun render(
        `in`: Bitmap,
        inBounds: Rect?,
        out: Bitmap,
        outBounds: Rect?,
        vararg layers: PixelateLayer
    ) {
        var outBounds = outBounds
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG or Paint.FILTER_BITMAP_FLAG)
        if (outBounds == null) {
            outBounds = Rect(0, 0, out.width, out.height)
        }
        render(`in`, inBounds, Canvas(out), outBounds, paint, *layers)
    }

   suspend fun render(
        `in`: Bitmap,
        inBounds: Rect?,
        canvas: Canvas,
        outBounds: Rect,
        paint: Paint,
        vararg layers: PixelateLayer
    ) {
        val inWidth = inBounds?.width() ?: `in`.width
        val inHeight = inBounds?.height() ?: `in`.height
        val inX = inBounds?.left ?: 0
        val inY = inBounds?.top ?: 0
        val scaleX = outBounds.width().toFloat() / inWidth
        val scaleY = outBounds.height().toFloat() / inHeight

        canvas.save()
        canvas.clipRect(outBounds)
        canvas.translate(outBounds.left.toFloat(), outBounds.top.toFloat())
        canvas.scale(scaleX, scaleY)

        for (layer in layers) {
            // option defaults
            val size = if (layer.size == null) layer.resolution else layer.size
            val cols = (inWidth / layer.resolution + 1).toInt()
            val rows = (inHeight / layer.resolution + 1).toInt()
            val halfSize = size / 2f
            val diamondSize = size / SQRT2
            val halfDiamondSize = diamondSize / 2f

            for (row in 0..rows) {
                val y = (row - 0.5f) * layer.resolution + layer.offsetY
                // normalize y so shapes around edges get color
                val pixelY = inY + Math.max(Math.min(y, (inHeight - 1).toFloat()), 0f)

                for (col in 0..cols) {
                    val x = (col - 0.5f) * layer.resolution + layer.offsetX
                    // normalize y so shapes around edges get color
                    val pixelX = inX + Math.max(Math.min(x, (inWidth - 1).toFloat()), 0f)

                    paint.color = getPixelColor(`in`, pixelX.toInt(), pixelY.toInt(), layer)

                    when (layer.shape) {
                        PixelateLayer.Shape.Circle -> canvas.drawCircle(x, y, halfSize, paint)
                        PixelateLayer.Shape.Diamond -> {
                            canvas.save()
                            canvas.translate(x, y)
                            canvas.rotate(45f)
                            canvas.drawRect(
                                -halfDiamondSize,
                                -halfDiamondSize,
                                halfDiamondSize,
                                halfDiamondSize,
                                paint
                            )
                            canvas.restore()
                        }
                        PixelateLayer.Shape.Square -> canvas.drawRect(
                            x - halfSize,
                            y - halfSize,
                            x + halfSize,
                            y + halfSize,
                            paint
                        )
                    } // switch
                } // col
            } // row
        }
        canvas.restore()
    }

    /**
     * Returns the color of the cluster. If options.enableDominantColor is true, return the
     * dominant color around the provided point. Return the color of the point itself otherwise.
     * The dominant color algorithm is based on simple counting search, so use with caution.
     *
     * @param pixels the bitmap
     * @param pixelX the x coordinate of the reference point
     * @param pixelY the y coordinate of the reference point
     * @param opts additional options
     * @return the color of the cluster
     */
    private suspend fun getPixelColor(pixels: Bitmap, pixelX: Int, pixelY: Int, opts: PixelateLayer): Int {
        var pixel = pixels.getPixel(pixelX, pixelY)
        if (opts.enableDominantColor) {
            // TODO: optimise dominant color algorithm
            val colorCounter = HashMap<Int, Int>(100)
            var x = 0f.coerceAtLeast(pixelX - opts.resolution).toInt()
            while (x < pixels.width.toFloat().coerceAtMost(pixelX + opts.resolution)) {
                var y = 0f.coerceAtLeast(pixelY - opts.resolution).toInt()
                while (y < pixels.height.toFloat().coerceAtMost(pixelY + opts.resolution)) {
                    val currentRGB = pixels.getPixel(x, y)
                    val count =
                        if (colorCounter.containsKey(currentRGB)) colorCounter[currentRGB] else 0
                    if (count != null) {
                        colorCounter[currentRGB] = count + 1
                    }
                    y++
                }
                x++
            }
            var max: Int? = null
            var dominantRGB: Int? = null
            for ((key, value) in colorCounter) {
                if (max == null || value > max) {
                    max = value
                    dominantRGB = key
                }
            }

            pixel = dominantRGB!!
        }
        val red = Color.red(pixel)
        val green = Color.green(pixel)
        val blue = Color.blue(pixel)
        val alpha = (opts.alpha * Color.alpha(pixel)).toInt()
        return Color.argb(alpha, red, green, blue)
    }
}
