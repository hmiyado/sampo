package com.example.hmiyado.sampo.domain.model

/**
 * Created by hmiyado on 2016/07/27.
 */
open class Location (
        val latitude: Double,
        val longitude: Double
){
    companion object{
        fun empty() = object:Location(0.0,0.0){
            override fun isEmpty(): Boolean{
                return true
            }
        }


    }

    override fun toString(): String {
        return "Location(${latitude}, ${longitude})"
    }

    open fun isEmpty(): Boolean {
        return false
    }
}