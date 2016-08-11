package com.example.hmiyado.sampo.domain.exception

/**
 * Created by hmiyado on 2016/08/11.
 */
class IllegalTypeOfTimeException: Throwable() {
    override val message: String?
        get() = "Illegal Type of Time"
}