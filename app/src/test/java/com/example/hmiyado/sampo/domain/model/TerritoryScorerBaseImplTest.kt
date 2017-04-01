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
        val period = ValidityPeriodImpl(Instant.EPOCH, Period.ofDays(1))

        val scorePerTerritory = TerritoryScorerBaseImpl.calcScorePerTerritory(neat.territories.first(), period)
        // stayBonus + carryingCapacity + ceil = 1290 くらい
        assertThat(scorePerTerritory, Matchers.closeTo(1290.0, 1290 * errorRate))

        val score = TerritoryScorerBaseImpl.calcScore(neat.territories, period)
        assertThat(score, Matchers.closeTo(1290.0, 1290.0 * errorRate))
    }

    @Test
    fun calcScoreHunter() {
        val hunter = LocationMockHunter(days = 1)
        val period = ValidityPeriodImpl(Instant.EPOCH, Period.ofDays(1))
        // stayBonus + 1 + 1
        val expectedScorePerTerritory = (TerritoryScorerBaseImpl.stayBonus + TerritoryScorerBaseImpl.transientScoreFunction(1.0) + TerritoryScorerBaseImpl.residentialScoreFunction(1.0))

        val score = TerritoryScorerBaseImpl.calcScore(hunter.territories, period)
        // (stayBonus + 1 + 1 ) * 24 * 60 * (24 * 60)^(1/4) = 106447 くらい
        val expectedTotalScore = expectedScorePerTerritory * hunter.territories.size * TerritoryScorerBaseImpl.areaScoreFunction(hunter.territories.size.toDouble())
        assertThat(score, Matchers.closeTo(expectedTotalScore, expectedTotalScore * 0.01))
    }

    @Test
    fun calcScoreSalaryMan() {
        val salaryMan = LocationMockSalaryMan(days = 1)
        val period = ValidityPeriodImpl(Instant.EPOCH, Period.ofDays(1))

        val score = TerritoryScorerBaseImpl.calcScore(salaryMan.territories, period)
        // 自宅または会社の概算スコア
        val nestScore = TerritoryScorerBaseImpl.stayBonus + TerritoryScorerBaseImpl.transientScoreFunction(TimeUnit.HOURS.toMinutes(11).toDouble()) + TerritoryScorerBaseImpl.residentialScoreFunction(TimeUnit.HOURS.toMinutes(11).toDouble())
        // 自宅と会社の間の概算スコア
        val wayScore = TerritoryScorerBaseImpl.stayBonus + TerritoryScorerBaseImpl.transientScoreFunction(2.0) + TerritoryScorerBaseImpl.residentialScoreFunction(1.0)
        val expectedTotalScore = (wayScore * 58 + nestScore * 2) * TerritoryScorerBaseImpl.areaScoreFunction(salaryMan.territories.size.toDouble())
        // 9032.7 くらい
        assertThat(score, Matchers.closeTo(expectedTotalScore, expectedTotalScore * errorRate))
    }

}