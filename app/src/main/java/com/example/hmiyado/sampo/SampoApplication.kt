package com.example.hmiyado.sampo

import android.app.Application
import com.example.hmiyado.sampo.controller.common.IntentDispatcher
import com.example.hmiyado.sampo.domain.math.Measurement
import com.example.hmiyado.sampo.domain.math.SphericalTrigonometry
import com.example.hmiyado.sampo.repository.compass.CompassSensor
import com.example.hmiyado.sampo.repository.compass.CompassSensorImpl
import com.example.hmiyado.sampo.repository.compass.CompassSensorVirtualImpl
import com.example.hmiyado.sampo.repository.location.*
import com.example.hmiyado.sampo.service.LocationSettingReceiver
import com.example.hmiyado.sampo.usecase.map.store.MapStore
import com.example.hmiyado.sampo.usecase.map.store.MapStoreImpl
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.android.androidModule
import com.github.salomonbrys.kodein.android.autoAndroidModule
import com.jakewharton.threetenabp.AndroidThreeTen
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

/**
 * Created by hmiyado on 2016/07/27.
 * Applicationクラス．
 * 実装を定義したり，Activityのライフサイクルに共通する処理を行ったりする．
 */
class SampoApplication : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(autoAndroidModule(this@SampoApplication))
        import(androidModule)

        if (BuildConfig.FLAVOR == "mock") {
            bind<LocationSensor>() with singleton { LocationSensorVirtualImpl() }
            bind<CompassSensor>() with singleton { CompassSensorVirtualImpl() }
        } else if (BuildConfig.FLAVOR == "real") {
            bind<LocationSensor>() with singleton { LocationSensorImpl(instance()) }
            bind<CompassSensor>() with singleton { CompassSensorImpl(instance()) }
        }
        bind<LocationRepository>() with autoScopedSingleton(androidActivityScope) { LocationRepositoryRealmImpl() }
        bind<LocationSettingReceiver>() with provider { LocationSettingReceiver() }
        bind<MapStore>() with singleton { MapStoreImpl() }
        bind<IntentDispatcher>() with singleton { IntentDispatcher(applicationContext) }
        bind<Measurement>() with singleton { SphericalTrigonometry }
    }


    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)
        Realm.init(baseContext)
        val realmConfig = RealmConfiguration.Builder()
                .name(BuildConfig.BUILD_TYPE + BuildConfig.FLAVOR)
                .schemaVersion(1)
                .build()
        if (BuildConfig.DEBUG) {
            Realm.deleteRealm(realmConfig)
        }
        Realm.setDefaultConfiguration(realmConfig)

        Timber.plant(Timber.DebugTree())
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}