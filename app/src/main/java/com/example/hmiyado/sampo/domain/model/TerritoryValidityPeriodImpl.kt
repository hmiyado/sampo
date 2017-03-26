package com.example.hmiyado.sampo.domain.model

import org.threeten.bp.Instant
import org.threeten.bp.Period

/**
 * Created by hmiyado on 2017/03/25.
 */
class TerritoryValidityPeriodImpl(
        override val start: Instant,
        override val period: Period
) : TerritoryValidityPeriod {
}