package com.example.hmiyado.sampo.usecase.map.interaction

import com.example.hmiyado.sampo.libs.plusAssign
import com.example.hmiyado.sampo.repository.location.LocationSensorState
import com.example.hmiyado.sampo.usecase.Interaction
import com.example.hmiyado.sampo.usecase.map.UseLocationSensor
import com.example.hmiyado.sampo.usecase.map.UseLocationSetting
import timber.log.Timber

/**
 * Created by hmiyado on 2017/03/01.
 */
class ControlLocationSensor(
        private val useLocationSetting: UseLocationSetting.Source,
        private val useLocationSensor: UseLocationSensor.Sink
) : Interaction() {
    init {
        subscriptions += useLocationSetting.getLocationSettingChangedSignal()
                .subscribe {
                    Timber.d(it.toString())
                    when (it) {
                        LocationSensorState.HIGH_ACCURACY -> {
                            useLocationSensor.start()
                        }
                        else                              -> {
                            useLocationSensor.stop()
                        }
                    }
                }
    }
}