package com.example.hmiyado.sampo.domain.model

import org.threeten.bp.Instant

/**
 * Created by hmiyado on 2016/07/27.
 *
 * 位置情報を表す
 *
 * @param latitude 緯度
 * @param longitude 経度
 * @param timeStamp 時間
 */
open class Location(
        /**
         * 緯度
         */
        val latitude: Double,
        /**
         * 経度
         */
        val longitude: Double,
        /**
         * 時間
         */
        val timeStamp: Instant
) {
    companion object {
        fun empty() = Location(0.0, 0.0, Instant.EPOCH)
    }

    override fun toString(): String {
        return "Location($latitude, $longitude, ${timeStamp.toString()})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Location

        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (timeStamp != other.timeStamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + timeStamp.hashCode()
        return result
    }
}