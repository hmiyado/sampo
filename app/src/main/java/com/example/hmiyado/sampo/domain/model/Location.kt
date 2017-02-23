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
        fun empty() = object : Location(0.0, 0.0, Instant.EPOCH) {
            override fun isEmpty(): Boolean {
                return true
            }
        }
    }

    override fun toString(): String {
        return "Location($latitude, $longitude, ${timeStamp.toString()})"
    }

    open fun isEmpty(): Boolean {
        return false
    }
}