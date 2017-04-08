package com.example.hmiyado.sampo.domain.model

import android.view.View
import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.math.Measurement
import com.example.hmiyado.sampo.domain.math.Vector2
import org.jetbrains.anko.dip

data class DrawableMap(
        val originalLocation: Location,
        val scaleFactor: Float,
        val rotateAngle: Degree,
        val measurement: Measurement
) {
    /**
     * [originalLocation] から location へのベクトルの水平成分と垂直成分(メートル)を計算する
     */
    fun determineVectorFromOrigin(location: Location): Vector2 = measurement.determineVector(originalLocation, location)

    /**
     * [originalLocation] から location への view 上でのベクトル(dip)を計算する
     */
    fun determineVectorFromOriginOnCanvas(view: View, location: Location): Vector2 {
        return determineVectorFromOrigin(location).run {
            Vector2(
                    calcScaledCoordinate(view, x),
                    calcScaledCoordinate(view, y)
            )
        }.rotate(rotateAngle)
    }

    fun calcScaledCoordinate(view: View, p: Double): Float = view.dip((p / scaleFactor).toFloat()).toFloat()
}