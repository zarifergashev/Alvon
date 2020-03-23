package com.ergashev_zarifjon.macho_man_pro.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FilterMenu.TABLE_NAME)
data class FilterMenu(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = PRIMARY_KEY) val id: Long,
    @ColumnInfo(name = FILTER_MENU_NAME) val title: String,
    @ColumnInfo(name = FILTER_MENU_IMG_URL) val imgUrl: String
) {
    companion object {
        const val TABLE_NAME: String = "filter_menu"
        const val PRIMARY_KEY = "primary_key"
        const val FILTER_MENU_NAME = "filter_menu_name"
        const val FILTER_MENU_IMG_URL = "filter_menu_img_url"
    }
}