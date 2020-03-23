package com.ergashev_zarifjon.macho_man_pro.data.dao

import androidx.room.*

@Dao
abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract  fun insert(vararg item: T)

    @Delete
    abstract  fun delete(vararg item: T)

    abstract  fun deleteAll()
}