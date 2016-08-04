package com.example.hmiyado.sampo.repository

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.kotlin.Time.LocalDateTime

/**
 * Created by hmiyado on 2016/08/04.
 */
interface LocationRepository {
    fun saveLocation(location: Location)
    fun loadLocationList(): List<Location>
    fun loadLocationList(startLocalDateTimeInclusive: LocalDateTime, endLocalDateTimeInclusive: LocalDateTime): List<Location>
}