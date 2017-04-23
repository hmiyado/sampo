package com.hmiyado.sampo.usecase.map.interaction

import com.github.salomonbrys.kodein.*
import com.hmiyado.sampo.usecase.Interaction
import com.hmiyado.sampo.usecase.map.MarkerProducer
import com.hmiyado.sampo.usecase.map.store.MapStore
import com.hmiyado.sampo.usecase.map.store.MapStoreImpl
import org.threeten.bp.Period

/**
 * Created by hmiyado on 2017/03/23.
 */

val mapUseCaseModule = Kodein.Module {
    bind<MapStore>() with singleton { MapStoreImpl(instance()) }
    bind<MarkerProducer>() with singleton { MarkerProducer(instance(), instance()) }

    bind<ControlLocationSensor>() with provider { ControlLocationSensor(instance(), instance()) }
    bind<DisplayScore>() with provider { DisplayScore(instance(), instance(), Period.ofDays(1), instance()) }
    bind<DrawCompass>() with provider { DrawCompass(instance(), instance()) }
    bind<DrawMap>() with provider { DrawMap(instance(), instance(), instance()) }
    bind<DrawMarkers>() with provider { DrawMarkers(instance(), instance()) }
    bind<DrawScale>() with provider { DrawScale(instance(), instance()) }
    bind<UpdateLocation>() with provider { UpdateLocation(instance(), instance()) }
    bind<UpdateTerritory>() with provider { UpdateTerritory(instance()) }
    bind<UpdateOrientation>() with provider { UpdateOrientation(instance(), instance()) }
    bind<UpdateRotateAngle>() with provider { UpdateRotateAngle(instance(), instance()) }
    bind<UpdateScale>() with provider { UpdateScale(instance(), instance()) }
    bind<UpdateValidityPeriodEnd>() with provider { UpdateValidityPeriodEnd(instance()) }
    bind<AddMarker>() with provider { AddMarker(instance(), instance()) }
    bind<SetMarkerAdderAvailability>() with provider { SetMarkerAdderAvailability(instance(), instance()) }
    bind<SaveMarker>() with provider { SaveMarker(instance(), instance()) }

    bind<List<Interaction<*>>>() with provider {
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
                instance<UpdateValidityPeriodEnd>(),
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