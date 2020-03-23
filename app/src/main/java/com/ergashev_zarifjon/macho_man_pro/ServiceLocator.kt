package com.ergashev_zarifjon.macho_man_pro

import android.content.Context
import com.ergashev_zarifjon.macho_man_pro.data.AppDatabas
import com.ergashev_zarifjon.macho_man_pro.data.repositorys.FilterMenuRepository
import com.ergashev_zarifjon.macho_man_pro.data.repositorys.FilterMenuRepositoryImpl

object ServiceLocator {

    private var filterMenuRepository: FilterMenuRepository? = null
    private var appDatabase: AppDatabas? = null


    fun provideFilterMenuRepository(context: Context) =
        filterMenuRepository ?: createFilterRepository(context)

    private fun createFilterRepository(context: Context): FilterMenuRepository {
        if (appDatabase == null)
            appDatabase = AppDatabas.getDatabaseInstance(context)
        return FilterMenuRepositoryImpl(
            appDatabase!!.filterMenuDao(),
            appDatabase!!.filterItemMenuDao()
        )
    }

}