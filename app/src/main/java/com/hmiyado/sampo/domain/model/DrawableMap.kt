package com.hmiyado.sampo.domain.model

import android.view.View
import com.hmiyado.sampo.domain.math.Degree
import com.hmiyado.sampo.domain.math.Measurement
import com.hmiyado.sampo.domain.math.SphericalTrigonometry
import com.hmiyado.sampo.domain.math.Vector2
import org.jetbrains.anko.dip

data class DrawableMap(
        val originalLocation: Location = Location.empty(),
        val scaleFactor: Float = 0f,
        val rotateAngle: Degree = Degree(0),
        val measurement: Measurement = SphericalTrigonometry
) {
    val areaRadius = scaleFactor.scale(Area.getRadius(measurement))

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


    fun calcScaledCoordinate(view: View, vector2: Vector2): Vector2 = Vector2(
            calcScaledCoordinate(view, vector2.x),
            calcScaledCoordinate(view, vector2.y)
    ).rotate(rotateAngle)

    fun calcScaledCoordinate(view: View, p: Double): Float = view.dip((p / scaleFactor).toFloat()).toFloat()

    private fun Float.scale(num: Double) = (num / this).toFloat()
}