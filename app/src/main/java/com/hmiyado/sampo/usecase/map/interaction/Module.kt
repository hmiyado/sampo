package com.hmiyado.sampo.usecase.map.interaction

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.MarkerProducer
import com.hmiyado.sampo.usecase.map.store.MapStore
import com.hmiyado.sampo.usecase.map.store.MapStoreImpl

/**
 * Created by hmiyado on 2017/03/23.
 */

val mapUseCaseModule = Kodein.Module {
    bind<MapStore>() with singleton { MapStoreImpl(instance()) }
    bind<MarkerProducer>() with singleton { MarkerProducer(instance(), instance()) }

    bind<ControlLocationSensor>() with singleton { ControlLocationSensor(instance(), instance()) }
    bind<DisplayScore>() with singleton { DisplayScore(instance(), instance(), instance()) }
    bind<DrawCompass>() with singleton { DrawCompass(instance(), instance()) }
    bind<DrawMap>() with singleton { DrawMap(instance(), instance(), instance()) }
    bind<DrawMarkers>() with singleton { DrawMarkers(instance(), instance()) }
    bind<DrawScale>() with singleton { DrawScale(instance(), instance()) }
    bind<UpdateLocation>() with singleton { UpdateLocation(instance(), instance()) }
    bind<UpdateTerritory>() with singleton { UpdateTerritory(instance()) }
    bind<UpdateOrientation>() with singleton { UpdateOrientation(instance(), instance()) }
    bind<UpdateRotateAngle>() with singleton { UpdateRotateAngle(instance(), instance()) }
    bind<UpdateScale>() with singleton { UpdateScale(instance(), instance()) }
    bind<AddMarker>() with singleton { AddMarker(instance(), instance()) }
    bind<SetMarkerAdderAvailability>() with singleton { SetMarkerAdderAvailability(instance(), instance()) }
    bind<SaveMarker>() with singleton { SaveMarker(instance(), instance()) }

    bind<List<Interaction<*>>>() with singleton {
        listOf(
                instance<ControlLocationSensor>(),
                instance<DisplayScore>(),
                instance<DrawCompass>(),
                instance<DrawMap>(),
                instance<DrawMarkers>(),
                instance<DrawScale>(),
                instance<UpdateOrientation>(),
                instance<UpdateRotateAngle>(),
                instance<UpdateScale>(),
                instance<AddMarker>(),
                instance<SetMarkerAdderAvailability>(),
                instance<SaveMarker>(),
                instance<UpdateLocation>(),
                instance<UpdateTerritory>()
        )
    }
}

val locationSensorUseCaseModule = Kodein.Module {
    //    bind<MapStore>() with singleton { MapStoreImpl(instance()) }
    bind<SaveLocation>() with singleton { SaveLocation(instance(), instance()) }

    bind<List<Interaction<*>>>() with singleton {
        listOf(
                instance<SaveLocation>()//,
        )
    }
}