package com.example.hmiyado.sampo.domain.model

import com.example.hmiyado.sampo.domain.model.mock.LocationMockNeat
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.threeten.bp.Instant
import org.threeten.bp.Period

/*
 * created by hmiyado on 2017/03/28.
 */
class TerritoryScorerBaseImplTest {
    @Test
    fun calcScore() {
        val neat = LocationMockNeat()
        val period = TerritoryValidityPeriodImpl(Instant.EPOCH, Period.ofWeeks(1))

        val score = TerritoryScorerBaseImpl.calcScore(neat.locations, period)
        assertThat(score, `is`(610.0))
    }

}