package com.ergashev_zarifjon.macho_man_pro.data.entity

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FilterItemMenu.TABLE_NAME)
data class FilterItemMenu(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = PRIMARY_KEY) val id: Long,
    @ColumnInfo(name = FILTER_PARENT_MENU_ID) val parentFilterId: String,
    @ColumnInfo(name = FILTER_PARENT_MENU_CODE) val code:String,
    @ColumnInfo(name = FILTER_ITEM_MENU_NAME) val title: String,
    @ColumnInfo(name = FILTER_ITEM_MENU_IMG_URL) val imgUrl: String
) {
    companion object {
        const val TABLE_NAME: String = "filter_item_menu"
        const val PRIMARY_KEY = "primary_key"
        const val FILTER_PARENT_MENU_ID = "filter_item_menu_id"
        const val FILTER_PARENT_MENU_CODE = "filter_item_menu_code"
        const val FILTER_ITEM_MENU_NAME = "filter_item_menu_name"
        const val FILTER_ITEM_MENU_IMG_URL = "filter_item_menu_img_url"

        val DIFF_UTIL=object : DiffUtil.ItemCallback<FilterItemMenu>(){
            override fun areItemsTheSame(
                oldItem: FilterItemMenu,
                newItem: FilterItemMenu
            ): Boolean = oldItem.code==oldItem.code

            override fun areContentsTheSame(
                oldItem: FilterItemMenu,
                newItem: FilterItemMenu
            ): Boolean = oldItem==newItem
        }
    }
}