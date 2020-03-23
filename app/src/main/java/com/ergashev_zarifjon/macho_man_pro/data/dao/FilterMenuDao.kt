package com.ergashev_zarifjon.macho_man_pro.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ergashev_zarifjon.macho_man_pro.data.entity.FilterItemMenu
import com.ergashev_zarifjon.macho_man_pro.data.entity.FilterMenu

@Dao
abstract class FilterMenuDao : BaseDao<FilterMenu>() {
    @Query("DELETE FROM ${FilterMenu.TABLE_NAME}")
    abstract override fun deleteAll()

    @Query("SELECT * FROM ${FilterMenu.TABLE_NAME}")
    abstract fun getAllFilterItemMenu(): LiveData<List<FilterMenu>>

    @Query("SELECT * FROM ${FilterMenu.TABLE_NAME} WHERE ${FilterMenu.PRIMARY_KEY}=:menuId")
    abstract  fun getFilterItemMenu(menuId:String): LiveData<FilterMenu>
}