package com.hmiyado.sampo.domain.math

import android.graphics.PointF

/**
 * Created by hmiyado on 2016/11/02.
 * 幾何学的な公式をおいておく
 */
object Geometry {

    /**
     * 点point11 と　点point12 を通る直線から，点point21と点point22を通る直線へのなす角 ¥theta を求める
     *
     * @param point11
     * @param point12
     * @param point21
     * @param point22
     * @return 角 ¥theta
     *
     */
    fun determineAngle(point11: PointF, point12: PointF, point21: PointF, point22: PointF): Radian {
        // 求める角¥theta は，点１１と１２を通る直線と平行な原点を通る直線から，点２１と点２２を通る直線と平行な原点を通る直線へのなす角に等しい
        // なので，点１１を原点となるように点１２を移動すればよい(点２１と点２２に関しても同様)
        return determineAngle(
                point12.x - point11.x,
                point12.y - point11.y,
                point22.x - point21.x,
                point22.y - point21.y
        )
    }

    /**
     * 原点と点(x1, y1) を通る直線から，原点と点(x2, y2)を通る直線へのなす角 ¥theta を返す
     * 単位はradian
     * 値域は `-pi < ¥theta < pi`
     * 例えば，determineAngle(1,1,1,0) は，原点と点(1,1)を通る直線から，原点と点(1,0)を通る直線へのなす角であるから，-pi/4 を返す
     *
     * @return ¥theta
     */
    private fun determineAngle(x1: Float, y1: Float, x2: Float, y2: Float): Radian {
        val theta1 = determineAngle(x1, y1)
        val theta2 = determineAngle(x2, y2)

        return theta2 - theta1
    }

    /**
     * 原点と点(x, y) を通る直線のなす角 ¥theta を求める
     * 値域は `-pi/2 < ¥theta <= pi/2`
     * @return ¥theta
     */
    private fun determineAngle(x: Float, y: Float): Radian {
        if (x.isZero()) {
            return Radian(Math.PI / 2)
        }
        return Radian(Math.atan((y / x).toDouble()))
    }
}