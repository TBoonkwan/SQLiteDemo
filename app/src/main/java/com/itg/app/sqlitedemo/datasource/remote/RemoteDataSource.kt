package com.itg.app.sqlitedemo.datasource.remote

import com.itg.app.sqlitedemo.entities.SyncDataResponse
import com.itg.app.sqlitedemo.service.ApiClient
import retrofit2.Callback

/**
 * Created by Teerapong on 11/05/2020
 */
class RemoteDataSource(private val apiClient: ApiClient) {

    fun getRemoteDataSource(callback: Callback<MutableList<SyncDataResponse>>) {
        apiClient.syncData().enqueue(callback)
    }
}