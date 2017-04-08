package com.example.hmiyado.sampo.domain.model

import org.threeten.bp.Instant
import org.threeten.bp.Period

/**
 * Created by hmiyado on 2017/03/25.
 */
data class ValidityPeriodImpl(
        override val begin: Instant,
        override val end: Instant
) : ValidityPeriod {
    constructor(start: Instant, period: Period) : this(start, start.plus(period))
}