package com.example.hmiyado.sampo.domain.math

/**
 * Created by hmiyado on 2016/11/02.
 * 幾何学的な公式をおいておく
 */
object Geometry {
    /**
     * 原点と点(x1, y1) を通る直線から，原点と点(x2, y2)を通る直線へのなす角 ¥theta を返す
     * 単位はradian
     * 値域は `-pi < ¥theta < pi`
     * 例えば，determineAngle(1,1,1,0) は，原点と点(1,1)を通る直線から，原点と点(1,0)を通る直線へのなす角であるから，-pi/4 を返す
     *
     * @return ¥theta
     */
    fun determineAngle(x1: Double, y1: Double, x2: Double, y2: Double): Double {
        val theta1 = determineAngle(x1, y1)
        val theta2 = determineAngle(x2, y2)

        return theta2 - theta1
    }

    /**
     * 原点と点(x, y) を通る直線のなす角 ¥theta を求める
     * 値域は `-pi/2 < ¥theta <= pi/2`
     * @return ¥theta
     */
    private fun determineAngle(x: Double, y: Double): Double {
        if ( x.isZero() ) {
            return Math.PI / 2
        }
        return Math.atan(y / x)

    }
}