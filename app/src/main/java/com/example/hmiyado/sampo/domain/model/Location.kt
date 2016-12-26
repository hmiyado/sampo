package com.example.hmiyado.sampo.domain.model

import com.example.hmiyado.sampo.domain.model.Time.LocalDateTime

/**
 * Created by hmiyado on 2016/07/27.
 *
 * 位置情報を表す
 *
 * @param latitude 緯度
 * @param longitude 経度
 * @param localDateTime 時間
 */
open class Location (
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
        val localDateTime: LocalDateTime
){
    companion object{
        fun empty() = object:Location(-100000.0,-100000.0, LocalDateTime.empty()){
            override fun isEmpty(): Boolean{
                return true
            }
        }
    }

    override fun toString(): String {
        return "Location($latitude, $longitude, ${localDateTime.toString()})"
    }

    open fun isEmpty(): Boolean {
        return false
    }
}