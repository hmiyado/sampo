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
    fun determineVectorFromOrigin(location: Location): Vector2 = measurement.determineVector(originalLocation, location)

    fun determineVectorFromOriginOnCanvas(view: View, location: Location): Vector2 {
        return determineVectorFromOrigin(location).run {
            Vector2(
                    calcScaledCoordinate(view, x),
                    calcScaledCoordinate(view, y)
            )
        }
    }

    fun calcScaledCoordinate(view: View, p: Double): Float = view.dip((p / scaleFactor).toFloat()).toFloat()
}