package com.ergashev_zarifjon.macho_man_pro.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ergashev_zarifjon.macho_man_pro.commons.Result
import com.ergashev_zarifjon.macho_man_pro.data.entity.FilterItemMenu

@Dao
abstract class FilterItemMenuDao : BaseDao<FilterItemMenu>() {
    @Query("DELETE FROM ${FilterItemMenu.TABLE_NAME}")
    abstract override  fun deleteAll()

    @Query("SELECT * FROM ${FilterItemMenu.TABLE_NAME}")
    abstract  fun getAllFilterItemMenu(): LiveData<List<FilterItemMenu>>

    @Query("SELECT * FROM ${FilterItemMenu.TABLE_NAME} WHERE ${FilterItemMenu.FILTER_PARENT_MENU_ID}=:parentId")
    abstract fun getFilterItemMenu(parentId:String): LiveData<List<FilterItemMenu>>

}