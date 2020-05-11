package com.itg.app.sqlitedemo.app

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.itg.app.sqlitedemo.di.appModules
import com.itg.app.sqlitedemo.di.networkModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Created by Teerapong on 11/05/2020
 */
class BaseApplication : MultiDexApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BaseApplication)
            modules(listOf(appModules, networkModules))
        }
    }
}