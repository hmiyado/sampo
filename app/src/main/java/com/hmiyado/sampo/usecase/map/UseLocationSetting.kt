package com.hmiyado.sampo.usecase.map

import com.hmiyado.sampo.repository.location.LocationSensorState
import io.reactivex.Observable

/**
 * Created by hmiyado on 2017/02/28.
 */
interface UseLocationSetting {
    interface Source {
        fun getLocationSettingChangedSignal(): Observable<LocationSensorState>
    }
}