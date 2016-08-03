package com.example.hmiyado.sampo.domain.model

import com.example.hmiyado.sampo.kotlin.Time.LocalDateTime

/**
 * Created by hmiyado on 2016/07/27.
 */
open class Location (
        val latitude: Double,
        val longitude: Double,
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
        return "Location(${latitude}, ${longitude}, ${localDateTime.toString()})"
    }

    open fun isEmpty(): Boolean {
        return false
    }
}