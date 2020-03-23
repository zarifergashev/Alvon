package com.ergashev_zarifjon.macho_man_pro.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ergashev_zarifjon.macho_man_pro.commons.APP_DATABASE_NAME
import com.ergashev_zarifjon.macho_man_pro.commons.APP_DATABASE_VERSION
import com.ergashev_zarifjon.macho_man_pro.data.dao.FilterItemMenuDao
import com.ergashev_zarifjon.macho_man_pro.data.dao.FilterMenuDao
import com.ergashev_zarifjon.macho_man_pro.data.entity.FilterItemMenu
import com.ergashev_zarifjon.macho_man_pro.data.entity.FilterMenu

@Database(entities = [FilterMenu::class, FilterItemMenu::class], version = APP_DATABASE_VERSION)
abstract class AppDatabas : RoomDatabase() {

    abstract fun filterItemMenuDao(): FilterItemMenuDao

    abstract fun filterMenuDao(): FilterMenuDao

    companion object {
        @Volatile
        var instance: AppDatabas? = null

        fun getDatabaseInstance(
            context: Context,
            succes: ((AppDatabas) -> Unit),
            fail: (Exception) -> Unit
        ) {
            if (instance != null) {
                succes.invoke(instance!!)
            } else {
                try {
                    synchronized(this) {
                        instance =
                            Room.databaseBuilder(
                                context, AppDatabas::class.java,
                                APP_DATABASE_NAME
                            )
                                .build().also {
                                    instance = it
                                    succes.invoke(it)
                                }
                    }
                } catch (e: Exception) {
                    fail.invoke(e)
                }
            }
        }

        fun getDatabaseInstance(
            context: Context
        ): AppDatabas {
            return if (instance != null) {
                (instance!!)
            } else {
                try {
                    synchronized(this) {
                        instance = Room.databaseBuilder(context, AppDatabas::class.java, APP_DATABASE_NAME)
                            .build()
                        instance!!
                    }
                } catch (e: Exception) {
                    throw e
                }
            }
        }
    }
}