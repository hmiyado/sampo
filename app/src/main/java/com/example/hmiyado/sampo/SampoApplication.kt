package com.example.hmiyado.sampo

import android.app.Application
import com.example.hmiyado.sampo.repository.LocationService
import com.example.hmiyado.sampo.repository.LocationServiceImpl
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber
import timber.log.Timber.Tree

/**
 * Created by hmiyado on 2016/07/27.
 */
class SampoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        Timber.plant(Timber.DebugTree())
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}