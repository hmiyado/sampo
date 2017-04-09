package com.hmiyado.sampo.domain.math

import java.lang.StrictMath.sqrt

/**
 * Created by hmiyado on 2017/03/02.
 */
object EuclidDistance : Measurement {
    override fun determinePathwayDistance(departureLatitude: Radian, departureLongitude: Radian, destinationLatitude: Radian, destinationLongitude: Radian): Double {
        return sqrt((departureLatitude - destinationLatitude).toDouble().square() + (departureLongitude - destinationLongitude).toDouble().square())
    }

    override fun determineAzimuth(departureLatitude: Radian, departureLongitude: Radian, destinationLatitude: Radian, destinationLongitude: Radian): Radian {
        throw NotImplementedError()
    }
}