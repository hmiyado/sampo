package com.example.hmiyado.sampo

import android.app.Application
import android.content.Intent
import com.example.hmiyado.sampo.repository.compass.CompassService
import com.example.hmiyado.sampo.repository.compass.CompassServiceImpl
import com.example.hmiyado.sampo.repository.compass.CompassServiceVirtualImpl
import com.example.hmiyado.sampo.repository.location.*
import com.example.hmiyado.sampo.service.LocationAndroidService
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.activityScope
import com.github.salomonbrys.kodein.android.androidModule
import com.github.salomonbrys.kodein.android.autoActivitySingleton
import com.jakewharton.threetenabp.AndroidThreeTen
import io.realm.Realm
import io.realm.RealmConfiguration
import org.jetbrains.anko.sensorManager
import timber.log.Timber

/**
 * Created by hmiyado on 2016/07/27.
 * Applicationクラス．
 * 実装を定義したり，Activityのライフサイクルに共通する処理を行ったりする．
 */
class SampoApplication : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(androidModule)

        bind<LocationService>() with singleton { LocationServiceVirtualImpl() }
        bind<LocationService>("real") with singleton { LocationServiceImpl(instance()) }
        bind<LocationRepository>() with autoActivitySingleton { LocationRepositoryRealmImpl() }
        bind<CompassService>("real") with singleton { CompassServiceImpl(sensorManager) }
        bind<CompassService>() with singleton { CompassServiceVirtualImpl() }
    }


    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        registerActivityLifecycleCallbacks(activityScope.lifecycleManager)
        Realm.init(baseContext)
        val realmConfig = RealmConfiguration.Builder()
                .name("Location.realm")
                .schemaVersion(1)
                .build()
        Realm.deleteRealm(realmConfig)
        Realm.setDefaultConfiguration(realmConfig)

        Timber.plant(Timber.DebugTree())
        startService(Intent(baseContext, LocationAndroidService::class.java)
                .putExtra(
                        LocationAndroidService.IntentType::class.simpleName,
                        LocationAndroidService.IntentType.START
                )
        )

    }

    override fun onTerminate() {
        super.onTerminate()
    }
}