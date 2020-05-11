package com.itg.app.sqlitedemo.service

import com.itg.app.sqlitedemo.entities.SyncDataResponse
import com.itg.app.sqlitedemo.util.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Teerapong on 11/05/2020
 */
interface ApiService {
    @GET("/upretail/synshow.php?token_api=${Constants.TOKEN}")
    fun syncData(@Query("action") action: String): Call<MutableList<SyncDataResponse>>
}