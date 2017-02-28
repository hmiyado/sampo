package com.example.hmiyado.sampo.controller.common

import android.content.Context
import com.example.hmiyado.sampo.service.LocationSensorService
import com.example.hmiyado.sampo.usecase.map.UseLocationSensor

/**
 * Created by hmiyado on 2017/03/01.
 */
class IntentDispatcher(private val context: Context) : UseLocationSensor.Sink {
    override fun start() {
        context.startService(LocationSensorService.IntentType.START.toIntent(context))
    }

    override fun stop() {
        context.startService(LocationSensorService.IntentType.STOP.toIntent(context))
    }
}