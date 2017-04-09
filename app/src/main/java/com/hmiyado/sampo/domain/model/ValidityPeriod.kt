package com.hmiyado.sampo.domain.model

import org.threeten.bp.Instant
import org.threeten.bp.Period

/**
 * Created by hmiyado on 2017/03/21.
 */
interface ValidityPeriod {
    companion object {
        fun create(start: Instant = Instant.EPOCH, period: Period = Period.ofDays(1)): ValidityPeriod = ValidityPeriodImpl(start, period)
        fun create(start: Instant, end: Instant): ValidityPeriod = ValidityPeriodImpl(start, end)
    }

    val begin: Instant
    val end: Instant

    fun isValid(instant: Instant): Boolean = instant.isBefore(end)

}