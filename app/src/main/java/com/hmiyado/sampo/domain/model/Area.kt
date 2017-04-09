package com.hmiyado.sampo.domain.model

import com.hmiyado.sampo.domain.math.Degree
import com.hmiyado.sampo.domain.math.Measurement

/**
 * Created by hmiyado on 2017/04/02.
 */
data class Area(
        val latitudeId: Int = 0,
        val longitudeId: Int = 0
) : Comparable<Area> {
    constructor(latitude: Degree, longitude: Degree) : this(
            Area.findLatitudeIdBy(latitude),
            Area.findLongitudeIdBy(longitude)
    )

    constructor(location: Location) : this(location.latitude, location.longitude)

    companion object {
        /**
         * 地球を東西方向および南北方向に分割するときの数
         */
        val DIVISION_NUMBER = 225855
        val LATITUDE_UNIT = Degree(180.0 / DIVISION_NUMBER)
        val LONGITUDE_UNIT = Degree(360.0 / DIVISION_NUMBER)

        fun findLatitudeIdBy(latitude: Degree): Int {
            return ((latitude + Degree(90)) / LATITUDE_UNIT).toInt()
        }

        fun findLongitudeIdBy(longitude: Degree): Int {
            return ((longitude + Degree(180)) / LONGITUDE_UNIT).toInt()
        }

        fun findLatitudeById(latitudeId: Int): Degree {
            return (LATITUDE_UNIT * latitudeId - Degree(90))
        }

        fun findLongitudeById(longitudeId: Int): Degree {
            return (LONGITUDE_UNIT * longitudeId - Degree(180))
        }

        fun getRadius(measurement: Measurement): Double {
            return measurement.determinePathwayDistance(
                    Location(0.0, 0.0),
                    Location(LATITUDE_UNIT, LONGITUDE_UNIT)
            ) / 2
        }
    }

    override fun compareTo(other: Area): Int {
        if (this == other) {
            return 0
        }

        if (latitudeId.compareTo(other.latitudeId) != 0) {
            return latitudeId.compareTo(other.latitudeId)
        } else {
            return longitudeId.compareTo(other.longitudeId)
        }
    }
}