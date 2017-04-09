package com.hmiyado.sampo

import android.app.Application
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.android.androidModule
import com.github.salomonbrys.kodein.android.autoAndroidModule
import com.hmiyado.sampo.controller.common.IntentDispatcher
import com.hmiyado.sampo.domain.math.Measurement
import com.hmiyado.sampo.domain.math.SphericalTrigonometry
import com.hmiyado.sampo.domain.model.SampoScorer
import com.hmiyado.sampo.domain.model.SampoScorerBaseImpl
import com.hmiyado.sampo.repository.compass.CompassSensor
import com.hmiyado.sampo.repository.compass.CompassSensorImpl
import com.hmiyado.sampo.repository.compass.CompassSensorVirtualImpl
import com.hmiyado.sampo.repository.location.LocationRepository
import com.hmiyado.sampo.repository.location.LocationSensor
import com.hmiyado.sampo.repository.location.LocationSensorImpl
import com.hmiyado.sampo.repository.location.LocationSensorVirtualImpl
import com.hmiyado.sampo.repository.marker.MarkerRepository
import com.hmiyado.sampo.repository.realm.LocationRepositoryRealmImpl
import com.hmiyado.sampo.repository.realm.MarkerRepositoryRealmImpl
import com.hmiyado.sampo.repository.realm.SampoRealmMigration
import com.hmiyado.sampo.service.LocationSettingReceiver
import com.hmiyado.sampo.usecase.map.store.MapStore
import com.hmiyado.sampo.usecase.map.store.MapStoreImpl
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
        bind<MarkerRepository>() with singleton { MarkerRepositoryRealmImpl() }
        bind<LocationSettingReceiver>() with singleton { LocationSettingReceiver() }
        bind<MapStore>() with singleton { MapStoreImpl(instance()) }
        bind<IntentDispatcher>() with singleton { IntentDispatcher(applicationContext) }
        bind<Measurement>() with singleton { SphericalTrigonometry }
        bind<SampoScorer>() with singleton { SampoScorerBaseImpl }
    }


    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)
        Realm.init(baseContext)
        val realmConfig = RealmConfiguration.Builder()
                .name(BuildConfig.BUILD_TYPE + BuildConfig.FLAVOR)
                .schemaVersion(2)
                .migration(SampoRealmMigration)
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