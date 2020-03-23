package com.ergashev_zarifjon.macho_man_pro.image_filter


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull

import androidx.recyclerview.widget.RecyclerView
import com.ergashev_zarifjon.macho_man_pro.R
import java.io.IOException
import java.io.InputStream

import ja.burhanrashid52.photoeditor.PhotoFilter

/**
 * @author [Burhanuddin Rashid](https://github.com/burhanrashid52)
 * @version 0.1.2
 * @since 5/23/2018
 */
class FilterViewAdapter(private val mFilterListener: FilterListener) :
    RecyclerView.Adapter<FilterViewAdapter.ViewHolder>() {
    private var mPairList = mutableListOf<Pair<String, PhotoFilter>>()

   fun setData(list: MutableList<Pair<String,PhotoFilter>>){
       mPairList=list
       notifyDataSetChanged()
   }
    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ei_image_filter_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val filterPair = mPairList[position]
        val fromAsset = getBitmapFromAsset(holder.itemView.context, filterPair.first)
        holder.mImageFilterView.setImageBitmap(fromAsset)
    }

    override fun getItemCount(): Int {
        return mPairList.size
    }

     inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImageFilterView: ImageView

        init {
            mImageFilterView = itemView.findViewById(R.id.iv_edit_type_row)
            itemView.setOnClickListener { mFilterListener.onFilterSelected(mPairList[layoutPosition].second) }
        }
    }

    private fun getBitmapFromAsset(context: Context, strName: String): Bitmap? {
        val assetManager = context.assets
        var istr: InputStream? = null
        try {
            istr = assetManager.open(strName)
            return BitmapFactory.decodeStream(istr)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }
companion object{
     fun setupFilters():MutableList<Pair<String, PhotoFilter>> {
        val mPairList= mutableListOf<Pair<String, PhotoFilter>>()
        mPairList.add(Pair("filters/original.jpg", PhotoFilter.NONE))
        mPairList.add(Pair("filters/auto_fix.png", PhotoFilter.AUTO_FIX))
        mPairList.add(Pair("filters/brightness.png", PhotoFilter.BRIGHTNESS))
        mPairList.add(Pair("filters/contrast.png", PhotoFilter.CONTRAST))
        mPairList.add(Pair("filters/documentary.png", PhotoFilter.DOCUMENTARY))
        mPairList.add(Pair("filters/dual_tone.png", PhotoFilter.DUE_TONE))
        mPairList.add(Pair("filters/fill_light.png", PhotoFilter.FILL_LIGHT))
        mPairList.add(Pair("filters/fish_eye.png", PhotoFilter.FISH_EYE))
        mPairList.add(Pair("filters/grain.png", PhotoFilter.GRAIN))
        mPairList.add(Pair("filters/gray_scale.png", PhotoFilter.GRAY_SCALE))
        mPairList.add(Pair("filters/lomish.png", PhotoFilter.LOMISH))
        mPairList.add(Pair("filters/negative.png", PhotoFilter.NEGATIVE))
        mPairList.add(Pair("filters/posterize.png", PhotoFilter.POSTERIZE))
        mPairList.add(Pair("filters/saturate.png", PhotoFilter.SATURATE))
        mPairList.add(Pair("filters/sepia.png", PhotoFilter.SEPIA))
        mPairList.add(Pair("filters/sharpen.png", PhotoFilter.SHARPEN))
        mPairList.add(Pair("filters/temprature.png", PhotoFilter.TEMPERATURE))
        mPairList.add(Pair("filters/tint.png", PhotoFilter.TINT))
        mPairList.add(Pair("filters/vignette.png", PhotoFilter.VIGNETTE))
        mPairList.add(Pair("filters/cross_process.png", PhotoFilter.CROSS_PROCESS))
        mPairList.add(Pair("filters/b_n_w.png", PhotoFilter.BLACK_WHITE))
        mPairList.add(Pair("filters/flip_horizental.png", PhotoFilter.FLIP_HORIZONTAL))
        mPairList.add(Pair("filters/flip_vertical.png", PhotoFilter.FLIP_VERTICAL))
        mPairList.add(Pair("filters/rotate.png", PhotoFilter.ROTATE))
         return mPairList
    }}
}