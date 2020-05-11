package com.itg.app.sqlitedemo.di

import androidx.room.Room
import com.itg.app.sqlitedemo.app.AppDatabase
import com.itg.app.sqlitedemo.service.ApiClient
import com.itg.app.sqlitedemo.service.ApiService
import com.itg.app.sqlitedemo.util.Constants.DB_NAME
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Teerapong on 11/05/2020
 */

val databaseModules = module {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, DB_NAME).build() }
    single { get<AppDatabase>().syncDataDAO() }
}