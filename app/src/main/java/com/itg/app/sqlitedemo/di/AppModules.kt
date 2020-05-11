package com.itg.app.sqlitedemo.di

import com.itg.app.sqlitedemo.MainActivity
import com.itg.app.sqlitedemo.MainActivityViewModel
import com.itg.app.sqlitedemo.datasource.local.LocalDataSource
import com.itg.app.sqlitedemo.datasource.remote.RemoteDataSource
import com.itg.app.sqlitedemo.repository.MainRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Teerapong on 11/05/2020
 */

val appModules = module {
    scope(named<MainActivity>()) {
        viewModel { MainActivityViewModel(get()) }
        factory { RemoteDataSource(get()) }
        factory { LocalDataSource(get()) }
        factory { MainRepository(get(), get()) }
    }
}
