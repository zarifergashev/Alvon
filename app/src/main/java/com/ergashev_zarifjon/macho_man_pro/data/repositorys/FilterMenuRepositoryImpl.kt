package com.ergashev_zarifjon.macho_man_pro.data.repositorys

import androidx.lifecycle.LiveData
import com.ergashev_zarifjon.macho_man_pro.commons.Result
import com.ergashev_zarifjon.macho_man_pro.data.dao.FilterItemMenuDao
import com.ergashev_zarifjon.macho_man_pro.data.dao.FilterMenuDao
import com.ergashev_zarifjon.macho_man_pro.data.entity.FilterItemMenu
import com.ergashev_zarifjon.macho_man_pro.data.entity.FilterMenu
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilterMenuRepositoryImpl(
    private val filterMenuDao: FilterMenuDao,
    private val filterItemMenuDao: FilterItemMenuDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FilterMenuRepository {
    override fun deleteAllFilterMenu() {
        filterMenuDao.deleteAll()
    }

    override fun deleteAllFilterItemMenu() {
        filterItemMenuDao.deleteAll()
    }

    override fun saveAllFilterMenu(vararg list: FilterMenu) {
        filterMenuDao.insert(*list)
    }

    override fun saveAllFilterItemMenu(vararg list: FilterItemMenu) {
        filterItemMenuDao.insert(*list)
    }

    override fun getAllFilterMenu() = filterMenuDao.getAllFilterItemMenu()


    override fun getFilterMenu(menuId: String) = filterMenuDao.getFilterItemMenu(menuId)

    override fun getAllFilterItemMenu() = filterItemMenuDao.getAllFilterItemMenu()

    override fun getFilterItemMenu(parentId: String) =
        filterItemMenuDao.getFilterItemMenu(parentId)
}
