package com.ergashev_zarifjon.macho_man_pro.model

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import com.ergashev_zarifjon.macho_man_pro.R

data class EditType(
    val type_code: String,
    val type_name: String,
    @DrawableRes val type_img: Int,
    @DrawableRes val background: Int = R.drawable.ei_bg_8,
    val filterImageSource:String?=""

) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<EditType>() {
            override fun areItemsTheSame(oldItem: EditType, newItem: EditType) = oldItem == newItem

            override fun areContentsTheSame(oldItem: EditType, newItem: EditType) =
                oldItem.type_code == newItem.type_code && oldItem.type_name == newItem.type_name && oldItem.background == newItem.background
        }
    }
}

data class FilterEditType(
    val type_code: String,
    val type_name: String,
    val type_img: Bitmap?
) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<FilterEditType>() {
            override fun areItemsTheSame(oldItem: FilterEditType, newItem: FilterEditType) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: FilterEditType, newItem: FilterEditType) =
                oldItem.type_code == newItem.type_code && oldItem.type_name == newItem.type_name
        }
    }
}