package com.hmiyado.sampo.usecase.event

/**
 * Created by hmiyado on 2017/04/22.
 */
sealed class Event {
    override fun toString(): String {
        return this::class.simpleName ?: "Anonymous Event"
    }
}

object LOCATION_UPDATED : Event()