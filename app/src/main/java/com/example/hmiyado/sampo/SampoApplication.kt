package com.example.hmiyado.sampo

import android.app.Application
import com.example.hmiyado.sampo.domain.usecase.UseLocation
import com.example.hmiyado.sampo.repository.compass.CompassService
import com.example.hmiyado.sampo.repository.compass.CompassServiceImpl
import com.example.hmiyado.sampo.repository.location.*
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.activityScope
import com.github.salomonbrys.kodein.android.androidModule
import com.github.salomonbrys.kodein.android.autoActivitySingleton
import com.jakewharton.threetenabp.AndroidThreeTen
import io.realm.Realm
import org.jetbrains.anko.sensorManager
import timber.log.Timber

/**
 * Created by hmiyado on 2016/07/27.
 * Applicationクラス．
 * 実装を定義したり，Activityのライフサイクルに共通する処理を行ったりする．
 */
class SampoApplication: Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(androidModule)

        bind<UseLocation>() with singleton { UseLocation(instance(), instance()) }
        bind<LocationService>() with singleton { LocationServiceVirtualImpl() }
        bind<LocationService>("real") with singleton { LocationServiceImpl(instance()) }
        bind<LocationRepository>() with autoActivitySingleton { LocationRepositoryRealmImpl() }
        bind<CompassService>() with singleton { CompassServiceImpl(sensorManager) }
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