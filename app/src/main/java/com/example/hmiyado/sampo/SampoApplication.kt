package com.example.hmiyado.sampo

import android.app.Application
import android.util.Log
import com.example.hmiyado.sampo.domain.usecase.UseLocation
import com.example.hmiyado.sampo.repository.*
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.activityScope
import com.github.salomonbrys.kodein.android.androidModule
import com.github.salomonbrys.kodein.android.autoActivitySingleton
import com.jakewharton.threetenabp.AndroidThreeTen
import io.realm.Realm
import timber.log.Timber
import timber.log.Timber.Tree

/**
 * Created by hmiyado on 2016/07/27.
 */
class SampoApplication: Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(androidModule)

        bind<UseLocation>() with provider { UseLocation(instance(), instance()) }
        bind<LocationService>() with provider { LocationServiceVirtualImpl() }
        bind<LocationRepository>() with autoActivitySingleton { LocationRepositoryRealmImpl()}
    }


    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        registerActivityLifecycleCallbacks(activityScope.lifecycleManager)
        Realm.init(baseContext)

        Timber.plant(Timber.DebugTree())
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}