package com.example.hmiyado.sampo

import android.app.Application
import android.content.Intent
import com.example.hmiyado.sampo.repository.compass.CompassService
import com.example.hmiyado.sampo.repository.compass.CompassServiceImpl
import com.example.hmiyado.sampo.repository.compass.CompassServiceVirtualImpl
import com.example.hmiyado.sampo.repository.location.*
import com.example.hmiyado.sampo.service.LocationAndroidService
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
            bind<LocationService>() with singleton { LocationServiceVirtualImpl() }
            bind<CompassService>() with singleton { CompassServiceVirtualImpl() }
        } else if (BuildConfig.FLAVOR == "real") {
            bind<LocationService>() with singleton { LocationServiceImpl(instance()) }
            bind<CompassService>() with singleton { CompassServiceImpl(instance()) }
        }
        bind<LocationRepository>() with autoScopedSingleton(androidActivityScope) { LocationRepositoryRealmImpl() }
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
        if (!BuildConfig.DEBUG) {
            Realm.deleteRealm(realmConfig)
        }
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