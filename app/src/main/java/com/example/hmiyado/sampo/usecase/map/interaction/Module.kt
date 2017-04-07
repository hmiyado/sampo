package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.usecase.Interaction
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton

/**
 * Created by hmiyado on 2017/03/23.
 */

val mapUseCaseModule = Kodein.Module {
    bind<ControlLocationSensor>() with singleton { ControlLocationSensor(instance(), instance()) }
    bind<DrawCompass>() with singleton { DrawCompass(instance(), instance()) }
    bind<DrawMap>() with singleton { DrawMap(instance(), instance(), instance()) }
    bind<DrawMarkers>() with singleton { DrawMarkers(instance(), instance()) }
    bind<DrawScale>() with singleton { DrawScale(instance(), instance()) }
    bind<UpdateOrientation>() with singleton { UpdateOrientation(instance(), instance()) }
    bind<UpdateRotateAngle>() with singleton { UpdateRotateAngle(instance(), instance()) }
    bind<UpdateScale>() with singleton { UpdateScale(instance(), instance()) }
    bind<AddMarker>() with singleton { AddMarker(instance(), instance()) }

    bind<List<Interaction<*>>>() with singleton {
        listOf(
                instance<ControlLocationSensor>(),
                instance<DrawCompass>(),
                instance<DrawMap>(),
                instance<DrawMarkers>(),
                instance<DrawScale>(),
                instance<UpdateOrientation>(),
                instance<UpdateRotateAngle>(),
                instance<UpdateScale>(),
                instance<AddMarker>()
        )
    }
}

val locationSensorUseCaseModule = Kodein.Module {
    bind<SaveLocation>() with singleton { SaveLocation(instance(), instance()) }
    bind<UpdateLocation>() with singleton { UpdateLocation(instance(), instance()) }
    bind<UpdateFootmarks>() with singleton { UpdateFootmarks(instance()) }
    bind<UpdateTerritory>() with singleton { UpdateTerritory(instance()) }

    bind<List<Interaction<*>>>() with singleton {
        listOf(
                instance<SaveLocation>(),
                instance<UpdateLocation>(),
                instance<UpdateFootmarks>(),
                instance<UpdateTerritory>()
        )
    }
}