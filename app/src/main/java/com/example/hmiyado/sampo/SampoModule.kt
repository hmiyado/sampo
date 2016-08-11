package com.example.hmiyado.sampo

import android.app.Application
import android.content.Context
import android.location.LocationManager
import com.example.hmiyado.sampo.domain.usecase.UseLocation
import com.example.hmiyado.sampo.repository.LocationRepositoryRealmImpl
import com.example.hmiyado.sampo.repository.LocationServiceImpl
import com.example.hmiyado.sampo.repository.LocationServiceVirtualImpl

/**
 * Created by hmiyado on 2016/08/05.
 */
object SampoModule {
    private var context: Context? = null
    val UseLocation: UseLocation by lazy {
        UseLocation(
                LocationServiceVirtualImpl(),
                //LocationServiceImpl(activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager),
                LocationRepositoryRealmImpl(context!!)
        )
    }

    fun attachActivity(activity: MainActivity) {
        context = activity.applicationContext
    }

}