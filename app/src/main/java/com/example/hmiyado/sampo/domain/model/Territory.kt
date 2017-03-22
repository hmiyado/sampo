package com.example.hmiyado.sampo.domain.model

import com.example.hmiyado.sampo.domain.math.Degree

/**
 * Created by hmiyado on 2017/03/21.
 */
class Territory(
        val latitudeId: Int,
        val longitudeId: Int,
        val locations: List<Location>,
        scorer: TerritoryScorer,
        territoryValidityPeriod: TerritoryValidityPeriod
) : Comparable<Territory>, TerritoryScorer by scorer, TerritoryValidityPeriod by territoryValidityPeriod {

    companion object {
        /**
         * 地球を東西方向および南北方向に分割するときの数
         */
        val DIVISION_NUMBER = 225855
        val LATITUDE_UNIT = 180.toDouble() / DIVISION_NUMBER
        val LONGITUDE_UNIT = 360.toDouble() / DIVISION_NUMBER

        fun findLatitudeIdBy(latitude: Degree): Int {
            return ((latitude.toDouble() + 90) / LATITUDE_UNIT).toInt()
        }

        fun findLongitudeIdBy(longitude: Degree): Int {
            return ((longitude.toDouble() + 180) / LONGITUDE_UNIT).toInt()
        }
    }

    val score
        get() = this.calcScore(locations, this)

    /**
     * 新しい位置情報を加えた縄張り情報を返す
     */
    fun addLocation(location: Location): Territory {
        return Territory(latitudeId, longitudeId, locations.plus(location), this, this)
    }

    override fun compareTo(other: Territory): Int {
        if (this == other) {
            return 0
        }

        if (latitudeId.compareTo(other.latitudeId) != 0) {
            return latitudeId.compareTo(other.latitudeId)
        } else {
            return longitudeId.compareTo(other.longitudeId)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Territory

        if (latitudeId != other.latitudeId) return false
        if (longitudeId != other.longitudeId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = latitudeId
        result = 31 * result + longitudeId
        return result
    }

}