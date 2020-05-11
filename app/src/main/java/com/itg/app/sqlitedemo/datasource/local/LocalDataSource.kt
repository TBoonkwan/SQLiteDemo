package com.itg.app.sqlitedemo.datasource.local

import com.itg.app.sqlitedemo.entities.SyncDataResponse
import com.itg.app.sqlitedemo.repository.DAORepository

/**
 * Created by Teerapong on 11/05/2020
 */
class LocalDataSource(private val dao: SyncDataDAO) : DAORepository {
    override fun insert(data: MutableList<SyncDataResponse>) {
        dao.insert(data)
    }

    override fun deleteAll() {
        dao.deleteAll()
    }

    override fun getAll(): MutableList<SyncDataResponse> {
        return dao.getAll()
    }

}