package com.hmiyado.sampo.repository.compass

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.hmiyado.sampo.domain.model.Orientation
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by hmiyado on 2016/11/30.
 * 方位を取得するサービスの実装．
 * http://techbooster.jpn.org/andriod/ui/443/
 */
class CompassSensorImpl(val sensorManager: SensorManager) : CompassSensor {

    private var magneticValues = floatArrayOf(0f, 0f, 0f)
    private var accelerometerValues = floatArrayOf(0f, 0f, 0f)

    private val compassServiceSubject: PublishSubject<Orientation> = PublishSubject.create()
    private val sensorEventListener: SensorEventListener = createSensorEventListener()

    init {
    }

    override fun getCompassService(): Observable<Orientation> {
        return compassServiceSubject.hide().share()
    }

    override fun startCompassService() {
        sensorManager.getSensorList(Sensor.TYPE_ALL).forEach {
            when (it.type) {
                Sensor.TYPE_MAGNETIC_FIELD -> {
                    sensorManager.registerListener(sensorEventListener, it, SensorManager.SENSOR_DELAY_UI)
                }
                Sensor.TYPE_ACCELEROMETER  -> {
                    sensorManager.registerListener(sensorEventListener, it, SensorManager.SENSOR_DELAY_UI)
                }
            }
        }
    }

    override fun stopCompassService() {
        sensorManager.unregisterListener(sensorEventListener)
    }


    private fun createSensorEventListener(): SensorEventListener {
        return object : SensorEventListener {
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                // 精度が変わったときに呼び出される．特にやることはない
            }

            override fun onSensorChanged(p0: SensorEvent?) {
                p0 ?: return
                if (p0.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) return
                when (p0.sensor.type) {
                    Sensor.TYPE_MAGNETIC_FIELD -> {
                        magneticValues = p0.values.clone()
                    }
                    Sensor.TYPE_ACCELEROMETER  -> {
                        accelerometerValues = p0.values.clone()
                    }
                }
                val inR = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
                val outR = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
                val orientationValues = floatArrayOf(0f, 0f, 0f)

                SensorManager.getRotationMatrix(inR, null, accelerometerValues, magneticValues)

                // Activity が縦向きのとき
                SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X, SensorManager.AXIS_Y, outR)
                SensorManager.getOrientation(outR, orientationValues)
                compassServiceSubject.onNext(Orientation.fromFloatArray(orientationValues))
            }
        }
    }

}