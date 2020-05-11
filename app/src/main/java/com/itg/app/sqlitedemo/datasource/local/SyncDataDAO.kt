package com.itg.app.sqlitedemo.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.itg.app.sqlitedemo.entities.SyncDataResponse

/**
 * Created by Teerapong on 11/05/2020
 */
@Dao
interface SyncDataDAO {
    @Insert
    fun insert(quote: MutableList<SyncDataResponse>)

    @Query("DELETE FROM sync_data_table")
    fun deleteAll()

    @Query("SELECT * FROM sync_data_table")
    fun getAll(): MutableList<SyncDataResponse>
}