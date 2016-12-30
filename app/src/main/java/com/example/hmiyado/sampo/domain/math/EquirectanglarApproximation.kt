package com.example.hmiyado.sampo.domain.math

/**
 * Created by hmiyado on 2016/12/30.
 *
 * @see Measurement の平面近似による実装．
 */
object EquirectanglarApproximation : Measurement {
    override fun determinePathwayDistance(departureLatitude: Radian, departureLongitude: Radian, destinationLatitude: Radian, destinationLongitude: Radian): Double {
        // http://www.movable-type.co.uk/scripts/latlong.html
        // #EquirectanglarApproximation
        val distNS = abs(departureLatitude - destinationLatitude)
        val distEW = abs(departureLongitude - destinationLongitude) * cos((departureLatitude + destinationLatitude) / 2)
        return Measurement.MEAN_RADIUS * Math.sqrt(distNS.toDouble().square() + distEW.toDouble().square())
    }

    override fun determineAzimuth(departureLatitude: Radian, departureLongitude: Radian, destinationLatitude: Radian, destinationLongitude: Radian): Radian {
        // 南北方向の変化量は y 軸方向の相対位置とみなせる
        val distNS = (destinationLatitude - departureLatitude)
        // 東西方向の変化量は x 軸方向の相対位置とみなせる
        val distEW = (destinationLongitude - departureLongitude) * cos((departureLatitude + destinationLatitude) / 2)
        return atan2(distEW.toDouble(), distNS.toDouble())
    }
}