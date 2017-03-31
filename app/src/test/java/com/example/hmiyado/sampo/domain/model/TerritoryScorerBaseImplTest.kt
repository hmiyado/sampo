package com.example.hmiyado.sampo.domain.model

import com.example.hmiyado.sampo.domain.model.mock.LocationMockHunter
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
    val errorRate = 0.01

    @Test
    fun calcScoreNeat() {
        val neat = LocationMockNeat(days = 1)
        val period = TerritoryValidityPeriodImpl(Instant.EPOCH, Period.ofDays(1))

        val score = TerritoryScorerBaseImpl.calcScore(neat.locations, period)
        assertThat(score, Matchers.closeTo(1290.0, 1290 * errorRate))
    }

    @Test
    fun calcScoreHunter() {
        val hunter = LocationMockHunter(days = 1)
        val period = TerritoryValidityPeriodImpl(Instant.EPOCH, Period.ofDays(1))
        val expectedScorePerTerritory = (TerritoryScorerBaseImpl.stayBonus + TerritoryScorerBaseImpl.transientScoreFunction(1.0) + TerritoryScorerBaseImpl.residentialScoreFunction(1.0))

        val score = hunter.territories.map {
            TerritoryScorerBaseImpl.calcScore(it.locations, period).apply {
                assertThat(this, Matchers.closeTo(expectedScorePerTerritory, expectedScorePerTerritory * errorRate))
            }
        }.sum()
        val expectedTotalScore = expectedScorePerTerritory * hunter.territories.size
        assertThat(score, Matchers.closeTo(expectedTotalScore, expectedTotalScore * 0.01))
    }

}