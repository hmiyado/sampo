package com.hmiyado.sampo.domain.model

/**
 * Created by hmiyado on 2017/04/02.
 *
 * 地点においとける
 */
data class Marker(
        val location: Location = Location(),
        val validityPeriod: ValidityPeriod = ValidityPeriod.create()
) {
    val area = Area(location)
}