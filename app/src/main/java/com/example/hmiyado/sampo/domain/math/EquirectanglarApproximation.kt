package com.example.hmiyado.sampo.domain.math

/**
 * Created by hmiyado on 2016/12/30.
 *
 * @see Measurement の平面近似による実装．
 */
object EquirectanglarApproximation : Measurement {
    private enum class PathwayOrientation {
        NORTH, WEST, SOUTH, EAST, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST
    }

    override fun determinePathwayDistance(departureLatitude: Radian, departureLongitude: Radian, destinationLatitude: Radian, destinationLongitude: Radian): Double {
        // http://www.movable-type.co.uk/scripts/latlong.html
        // #EquirectanglarApproximation
        val deltaLongitude = if (Measurement.isOver180thMeridian(departureLongitude, destinationLongitude)) {
            2 * Radian.PI - abs(departureLongitude - destinationLongitude)
        } else {
            abs(departureLongitude - destinationLongitude)
        }
        val distNS = abs(departureLatitude - destinationLatitude)
        val distEW = determineLongitudePathway(deltaLongitude, departureLatitude, destinationLatitude)
        return Measurement.MEAN_RADIUS * Math.sqrt(distNS.toDouble().square() + distEW.toDouble().square())
    }

    override fun determineAzimuth(departureLatitude: Radian, departureLongitude: Radian, destinationLatitude: Radian, destinationLongitude: Radian): Radian {
        val deltaLongitude = Radian.min(
                abs(destinationLongitude - departureLongitude),
                2 * Radian.PI - abs(destinationLongitude - departureLongitude)
        )
        // 南北方向の変化量は y 軸方向の相対位置とみなせる
        val distNS = abs(destinationLatitude - departureLatitude)
        // 東西方向の変化量は x 軸方向の相対位置とみなせる
        val distEW = determineLongitudePathway(deltaLongitude, departureLatitude, destinationLatitude)
        val angle = atan2(distNS.toDouble(), distEW.toDouble())
        val pathwayOrientation = if (Measurement.isOver180thMeridian(departureLongitude, destinationLongitude)) {
            // 180度子午線を越えている経路は，経度の大小関係と経路の東西方向が逆になる
            // つまり，出発点の経度＞到着地の経度なら，経路は東方向
            when {
                departureLatitude == destinationLatitude ->
                    when {
                        departureLongitude < destinationLongitude -> PathwayOrientation.WEST
                        departureLongitude == destinationLongitude -> PathwayOrientation.NORTH
                        abs(departureLongitude) == Radian.PI && abs(destinationLongitude) == Radian.PI -> PathwayOrientation.NORTH
                        else -> PathwayOrientation.EAST
                    }
                departureLatitude < destinationLatitude -> // 経路は北方向
                    if (departureLongitude < destinationLongitude) {
                        PathwayOrientation.NORTH_WEST
                    } else {
                        PathwayOrientation.NORTH_EAST
                    }
                else -> // 経路は南方向
                    if (departureLongitude < destinationLongitude) {
                        PathwayOrientation.SOUTH_WEST
                    } else {
                        PathwayOrientation.SOUTH_EAST
                    }
            }
        } else {
            when {
                departureLatitude == destinationLatitude ->
                    when {
                        departureLongitude < destinationLongitude -> PathwayOrientation.EAST
                        departureLongitude == destinationLongitude -> PathwayOrientation.NORTH
                        else -> PathwayOrientation.WEST
                    }
                departureLatitude < destinationLatitude -> // 経路は北方向
                    if (departureLongitude < destinationLongitude) {
                        // 経路は北東方向．つまり第一象限
                        PathwayOrientation.NORTH_EAST
                    } else {
                        // 経路は北西方向．つまり第四象限
                        PathwayOrientation.NORTH_WEST
                    }
                else -> // 経路は南方向
                    if (departureLongitude < destinationLongitude) {
                        // 経路は南東方向．つまり第二象限
                        PathwayOrientation.SOUTH_EAST
                    } else {
                        // 経路は南西方向．つまり第三象限
                        PathwayOrientation.SOUTH_WEST
                    }
            }
        }
        return when (pathwayOrientation) {
            EquirectanglarApproximation.PathwayOrientation.NORTH -> Radian.ZERO
            EquirectanglarApproximation.PathwayOrientation.WEST -> 3 * Radian.PI / 2
            EquirectanglarApproximation.PathwayOrientation.SOUTH -> Radian.PI
            EquirectanglarApproximation.PathwayOrientation.EAST -> Radian.PI / 2
            EquirectanglarApproximation.PathwayOrientation.NORTH_WEST -> 3 * Radian.PI / 2 + angle
            EquirectanglarApproximation.PathwayOrientation.NORTH_EAST -> Radian.PI / 2 - angle
            EquirectanglarApproximation.PathwayOrientation.SOUTH_WEST -> 3 * Radian.PI / 2 - angle
            EquirectanglarApproximation.PathwayOrientation.SOUTH_EAST -> Radian.PI / 2 + angle
        }
    }

    private fun determineLongitudePathway(deltaLongitude: Radian, departureLatitude: Radian, destinationLatitude: Radian) = deltaLongitude * cos((departureLatitude + destinationLatitude) / 2)
}