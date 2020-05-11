package com.itg.app.sqlitedemo.di

import androidx.room.Room
import com.itg.app.sqlitedemo.app.AppDatabase
import com.itg.app.sqlitedemo.datasource.local.LocalDataSource
import com.itg.app.sqlitedemo.datasource.remote.RemoteDataSource
import com.itg.app.sqlitedemo.repository.MainRepository
import com.itg.app.sqlitedemo.service.ApiClient
import com.itg.app.sqlitedemo.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Teerapong on 11/05/2020
 */

val networkModules = module {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "app_database").build() }
    single { get<AppDatabase>().syncDataDAO() }

    single { provideLoggingInterceptor() }
    single { provideOkHttp(get()) }
    single { provideRetrofit(get()) }
    single { provideApi(get()) }

    factory { RemoteDataSource(get()) }
    factory { LocalDataSource(get()) }
    factory { MainRepository(get(), get() , get()) }
}

fun provideApi(retrofit: Retrofit): ApiClient {
    val service = retrofit.create(ApiService::class.java)
    return ApiClient(service)
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://tracking-ems.net")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideOkHttp(logging: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
