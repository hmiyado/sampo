package com.hmiyado.sampo.domain.model

import com.hmiyado.sampo.domain.math.Degree
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
        val latitude: Degree = Degree(0),
        /**
         * 経度
         */
        val longitude: Degree = Degree(0),
        /**
         * 時間
         */
        val timeStamp: Instant = Instant.EPOCH
) {
    companion object {
        fun empty() = Location()
    }

    constructor(lat: Double, long: Double, timeStamp: Instant = Instant.EPOCH) : this(Degree(lat), Degree(long), timeStamp)

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