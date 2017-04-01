package com.example.hmiyado.sampo.domain.model

import com.example.hmiyado.sampo.domain.model.mock.LocationMockHunter
import com.example.hmiyado.sampo.domain.model.mock.LocationMockNeat
import com.example.hmiyado.sampo.domain.model.mock.LocationMockSalaryMan
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Test
import org.threeten.bp.Instant
import org.threeten.bp.Period
import java.util.concurrent.TimeUnit

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
        // stayBonus + carryingCapacity + ceil = 1290 くらい
        assertThat(score, Matchers.closeTo(1290.0, 1290 * errorRate))
    }

    @Test
    fun calcScoreHunter() {
        val hunter = LocationMockHunter(days = 1)
        val period = TerritoryValidityPeriodImpl(Instant.EPOCH, Period.ofDays(1))
        // stayBonus + 1 + 1
        val expectedScorePerTerritory = (TerritoryScorerBaseImpl.stayBonus + TerritoryScorerBaseImpl.transientScoreFunction(1.0) + TerritoryScorerBaseImpl.residentialScoreFunction(1.0))

        val score = hunter.territories.map {
            TerritoryScorerBaseImpl.calcScore(it.locations, period).apply {
                assertThat(this, Matchers.closeTo(expectedScorePerTerritory, expectedScorePerTerritory * errorRate))
            }
        }.sum()
        // (stayBonus + 1 + 1 ) * 24 * 60 = 17280 くらい
        val expectedTotalScore = expectedScorePerTerritory * hunter.territories.size
        assertThat(score, Matchers.closeTo(expectedTotalScore, expectedTotalScore * 0.01))
    }

    @Test
    fun calcScoreSalaryMan() {
        val salaryMan = LocationMockSalaryMan(days = 1)
        val period = TerritoryValidityPeriodImpl(Instant.EPOCH, Period.ofDays(1))

        val score = salaryMan.territories.map {
            val score = TerritoryScorerBaseImpl.calcScore(it.locations, period)
            score
        }.sum()
        // 自宅または会社の概算スコア
        val nestScore = TerritoryScorerBaseImpl.stayBonus + TerritoryScorerBaseImpl.transientScoreFunction(TimeUnit.HOURS.toMinutes(11).toDouble()) + TerritoryScorerBaseImpl.residentialScoreFunction(TimeUnit.HOURS.toMinutes(11).toDouble())
        // 自宅と会社の間の概算スコア
        val wayScore = TerritoryScorerBaseImpl.stayBonus + TerritoryScorerBaseImpl.transientScoreFunction(2.0) + TerritoryScorerBaseImpl.residentialScoreFunction(1.0)
        val expectedTotalScore = wayScore * 58 + nestScore * 2
        // 3232.1 くらい
        assertThat(score, Matchers.closeTo(expectedTotalScore, expectedTotalScore * errorRate))
    }

}