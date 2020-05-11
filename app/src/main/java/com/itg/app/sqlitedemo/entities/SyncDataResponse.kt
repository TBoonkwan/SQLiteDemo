package com.itg.app.sqlitedemo.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


/**
 * Created by Teerapong on 11/05/2020
 */
@Entity(tableName = "sync_data_table")
data class SyncDataResponse(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @SerializedName("Retailer_id")
    var retailerId: String? = "",
    @SerializedName("Status")
    var status: String? = "",
    @SerializedName("Title")
    var title: String? = ""
)