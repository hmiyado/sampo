package com.example.hmiyado.sampo.usecase.map

import com.example.hmiyado.sampo.repository.location.LocationSensorState
import rx.Observable

/**
 * Created by hmiyado on 2017/02/28.
 */
interface UseLocationSetting {
    interface Source {
        fun getLocationSettingChangedSignal(): Observable<LocationSensorState>
    }
}