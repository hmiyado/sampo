package com.example.hmiyado.sampo

import android.app.Application
import android.util.Log
import com.example.hmiyado.sampo.repository.LocationService
import com.example.hmiyado.sampo.repository.LocationServiceImpl
import com.jakewharton.threetenabp.AndroidThreeTen

/**
 * Created by hmiyado on 2016/07/27.
 */
class SampoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}