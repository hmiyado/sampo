package com.example.hmiyado.sampo

import android.app.Application
import com.example.hmiyado.sampo.repository.LocationService
import com.example.hmiyado.sampo.repository.LocationServiceImpl

/**
 * Created by hmiyado on 2016/07/27.
 */
class SampoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

    }

    override fun onTerminate() {
        super.onTerminate()
    }
}