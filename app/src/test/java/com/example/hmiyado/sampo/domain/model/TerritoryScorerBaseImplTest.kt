package com.example.hmiyado.sampo.domain.model

import com.example.hmiyado.sampo.domain.model.mock.LocationMockNeat
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Test
import org.threeten.bp.Instant
import org.threeten.bp.Period

/*
 * created by hmiyado on 2017/03/28.
 */
class TerritoryScorerBaseImplTest {
    @Test
    fun calcScore() {
        val neat = LocationMockNeat(days = 1)
        val period = TerritoryValidityPeriodImpl(Instant.EPOCH, Period.ofDays(1))

        val score = TerritoryScorerBaseImpl.calcScore(neat.locations, period)
        assertThat(score, Matchers.closeTo(1290.0, 1290 * 0.1))
    }

}