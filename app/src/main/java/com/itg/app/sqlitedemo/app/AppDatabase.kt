package com.itg.app.sqlitedemo.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.itg.app.sqlitedemo.dao.SyncDataDAO
import com.itg.app.sqlitedemo.entities.SyncDataResponse

/**
 * Created by Teerapong on 11/05/2020
 */
@Database(entities = [SyncDataResponse::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun syncDataDAO() : SyncDataDAO
}