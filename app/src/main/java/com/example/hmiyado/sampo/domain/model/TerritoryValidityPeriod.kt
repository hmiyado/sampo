package com.example.hmiyado.sampo.domain.model

import org.threeten.bp.Instant
import org.threeten.bp.Period

/**
 * Created by hmiyado on 2017/03/21.
 */
interface TerritoryValidityPeriod {
    val start: Instant
    val period: Period

    fun isValid(instant: Instant): Boolean = instant.isBefore(start.plus(period))

}