package com.itg.app.sqlitedemo.repository

import com.itg.app.sqlitedemo.datasource.local.LocalDataSource
import com.itg.app.sqlitedemo.datasource.remote.RemoteDataSource
import com.itg.app.sqlitedemo.entities.SyncDataResponse
import retrofit2.Callback

/**
 * Created by Teerapong on 11/05/2020
 */
class MainRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : DAORepository {

    fun getRemoteDataSource(
        action: String,
        callback: Callback<MutableList<SyncDataResponse>>
    ) {
        remoteDataSource.getRemoteDataSource(action, callback)
    }

    override fun insert(data: MutableList<SyncDataResponse>) {
        localDataSource.insert(data)
    }

    override fun deleteAll() {
        localDataSource.deleteAll()
    }

    override fun getAll(): MutableList<SyncDataResponse> {
        return localDataSource.getAll()
    }
}

interface DAORepository {
    fun insert(data: MutableList<SyncDataResponse>)

    fun deleteAll()

    fun getAll(): MutableList<SyncDataResponse>
}