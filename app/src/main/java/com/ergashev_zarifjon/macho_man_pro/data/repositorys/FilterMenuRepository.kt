package com.ergashev_zarifjon.macho_man_pro.data.repositorys

import androidx.lifecycle.LiveData
import com.ergashev_zarifjon.macho_man_pro.commons.Result
import com.ergashev_zarifjon.macho_man_pro.data.entity.FilterItemMenu
import com.ergashev_zarifjon.macho_man_pro.data.entity.FilterMenu

interface FilterMenuRepository {
    fun getAllFilterMenu(): LiveData<List<FilterMenu>>

    fun saveAllFilterMenu(vararg list: FilterMenu)

    fun deleteAllFilterMenu()

    fun getFilterMenu(menuId: String): LiveData<FilterMenu>

    fun getAllFilterItemMenu(): LiveData<List<FilterItemMenu>>

    fun saveAllFilterItemMenu(vararg list: FilterItemMenu)

    fun deleteAllFilterItemMenu()

    fun getFilterItemMenu(parentId: String): LiveData<List<FilterItemMenu>>

}