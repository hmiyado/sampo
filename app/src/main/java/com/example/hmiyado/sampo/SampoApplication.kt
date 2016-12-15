package com.example.hmiyado.sampo

import android.app.Application
import com.example.hmiyado.sampo.controller.MapViewController
import com.example.hmiyado.sampo.domain.store.Store
import com.example.hmiyado.sampo.domain.usecase.map.*
import com.example.hmiyado.sampo.presenter.MapViewPresenter
import com.example.hmiyado.sampo.repository.compass.CompassService
import com.example.hmiyado.sampo.repository.compass.CompassServiceImpl
import com.example.hmiyado.sampo.repository.compass.CompassServiceVirtualImpl
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
class SampoApplication : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(androidModule)

        bind<LocationService>() with singleton { LocationServiceVirtualImpl() }
        bind<LocationService>("real") with singleton { LocationServiceImpl(instance()) }
        bind<LocationRepository>() with autoActivitySingleton { LocationRepositoryRealmImpl() }
        bind<CompassService>("real") with singleton { CompassServiceImpl(sensorManager) }
        bind<CompassService>() with singleton { CompassServiceVirtualImpl() }

        bind<StoreToMapViewerOutputInteraction>() with factory { pair: Pair<Store, MapViewController> ->
            StoreToMapViewerOutputInteraction(
                    pair.first,
                    factory<MapViewController, UseMapViewerOutput>()(pair.second)
            )
        }
        bind<MapViewerInputToMapViewerOutputInteraction>() with factory { pair: Pair<MapViewPresenter, MapViewController> ->
            MapViewerInputToMapViewerOutputInteraction(
                    factory<MapViewPresenter, UseMapViewerInput>()(pair.first),
                    factory<MapViewController, UseMapViewerOutput>()(pair.second)
            )
        }
        bind<MapViewerInputToStoreInteraction>() with factory { pair: Pair<MapViewPresenter, Store> ->
            MapViewerInputToStoreInteraction(
                    factory<MapViewPresenter, UseMapViewerInput>()(pair.first),
                    pair.second
            )
        }
        bind<UseMapViewerInput>() with factory { mapViewPresenter: MapViewPresenter -> UseMapViewerInput(mapViewPresenter) }
        bind<UseMapViewerOutput>() with factory { mapViewController: MapViewController -> UseMapViewerOutput(mapViewController) }
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