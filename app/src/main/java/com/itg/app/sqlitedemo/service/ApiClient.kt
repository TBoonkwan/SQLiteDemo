package com.itg.app.sqlitedemo.service


/**
 * Created by Teerapong on 11/05/2020
 */
class ApiClient(private val service: ApiService) {

    fun syncData() = service.syncData()
}