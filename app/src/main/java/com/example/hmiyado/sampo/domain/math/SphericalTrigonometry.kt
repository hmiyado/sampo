package com.example.hmiyado.sampo.domain.math

/**
 * Created by hmiyado on 2016/12/28.
 *
 * 球面三角法によるMeasurementの実装
 */
object SphericalTrigonometry : Measurement {
    override fun determinePathwayDistance(departureLatitude: Radian, departureLongitude: Radian, destinationLatitude: Radian, destinationLongitude: Radian): Double {
        return Measurement.AVERAGE * determinePathwayAngle(departureLatitude, departureLongitude, destinationLatitude, destinationLongitude).toDouble()
    }

    override fun determineAzimuth(departureLatitude: Radian, departureLongitude: Radian, destinationLatitude: Radian, destinationLongitude: Radian): Radian {
        val pathwayAngle = determinePathwayAngle(departureLatitude, departureLongitude, destinationLatitude, destinationLongitude)
        if (pathwayAngle == Radian.ZERO) {
            return Radian.ZERO
        }
        val angle = acos(
                (cos(Radian.PI / 2 - destinationLatitude) - cos(Radian.PI / 2 - departureLatitude) * cos(pathwayAngle))
                        / (sin(Radian.PI / 2 - departureLatitude) * sin(pathwayAngle))
        )
        return if (departureLongitude < destinationLongitude) {
            angle
        } else {
            // 出発地が到着地より東にあるときは，求める角すなわち，緯線から経路への右回りの角の求め方が少し変わる
            2 * Radian.PI - angle
        }
    }

    /**
     * 出発地と到着地の経路が球の中心に対してなす角を求める
     */
    private fun determinePathwayAngle(departureLatitude: Radian, departureLongitude: Radian, destinationLatitude: Radian, destinationLongitude: Radian): Radian {
        return determineByCosineFormula(
                Radian.PI / 2 - departureLatitude,
                Radian.PI / 2 - destinationLatitude,
                abs(destinationLongitude - departureLongitude)
        )
    }

    /**
     * 球面三角において，余弦定理を用いる．
     *
     * @param b 球面三角の辺の部分の，円弧の角度
     * @param c 球面三角の辺の部分の，円弧の角度
     * @param A b と c に挟まれた部分の角度
     * @return 球面三角上で，A に向かい合う円弧の角度
     */
    private fun determineByCosineFormula(b: Radian, c: Radian, A: Radian): Radian {
        return acos(cos(b) * cos(c) + sin(b) * sin(c) * cos(A))
    }
}