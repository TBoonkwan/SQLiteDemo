package com.itg.app.sqlitedemo.service

import com.itg.app.sqlitedemo.entities.SyncDataResponse
import retrofit2.Call
import retrofit2.http.GET


/**
 * Created by Teerapong on 11/05/2020
 */
interface ApiService {
    @GET("/upretail/synshow.php?token_api=123456798&action=showData")
    fun syncData(): Call<MutableList<SyncDataResponse>>
}