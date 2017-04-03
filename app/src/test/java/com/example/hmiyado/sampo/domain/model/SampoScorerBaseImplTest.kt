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
class SampoScorerBaseImplTest {
    /**
     * 等比級数の和
     */
    fun sumOfGeometricSequence(initial: Double, rate: Double, num: Long): Double {
        return initial * (1 - Math.pow(rate, num.toDouble())) / (1 - rate)
    }

    val errorRate = 0.01

    @Test
    fun calcScoreNeat() {
        val markerNum = 3
        val neat = LocationMockNeat(days = 1, markerNum = markerNum.toLong())
        val period = ValidityPeriodImpl(Instant.EPOCH, Period.ofDays(1))

        val score = SampoScorerBaseImpl.calcScore(neat.territories, neat.markers, period)
        val expectedScore = SampoScorerBaseImpl.run {
            val markerImpact = sumOfGeometricSequence(markerDefaultImpact, markerImpactDecreasingRate, markerNum.toLong())
            (stayBonus + transientCeil + residentialCapacity) * (1 + markerImpact)
        }
        // (stayBonus[10] + ceil[200] + capacity[1080]) * 2.75 = 3547.5 くらい
        assertThat(score, Matchers.closeTo(expectedScore, expectedScore * errorRate))
    }

    @Test
    fun calcScoreHunter() {
        val hunter = LocationMockHunter(days = 1)
        val period = ValidityPeriodImpl(Instant.EPOCH, Period.ofDays(1))
        // stayBonus + 1 + 1
        val expectedScorePerTerritory = (SampoScorerBaseImpl.stayBonus + SampoScorerBaseImpl.transientScoreFunction(1.0) + SampoScorerBaseImpl.residentialScoreFunction(1.0))

        val score = SampoScorerBaseImpl.calcScore(hunter.territories, hunter.markers, period)
        // (stayBonus + 1 + 1 ) * 24 * 60 * (24 * 60)^(1/4) = 106447 くらい
        val expectedTotalScore = expectedScorePerTerritory * hunter.territories.size * SampoScorerBaseImpl.areaScoreFunction(hunter.territories.size.toDouble())
        assertThat(score, Matchers.closeTo(expectedTotalScore, expectedTotalScore * 0.01))
    }

    @Test
    fun calcScoreSalaryMan() {
        val markerNum = 3L
        val salaryMan = LocationMockSalaryMan(days = 1, markerNum = markerNum)
        val period = ValidityPeriodImpl(Instant.EPOCH, Period.ofDays(1))

        val score = SampoScorerBaseImpl.calcScore(salaryMan.territories, salaryMan.markers, period)
        // 自宅または会社の概算スコア
        val nestScore = SampoScorerBaseImpl.stayBonus + SampoScorerBaseImpl.transientScoreFunction(TimeUnit.HOURS.toMinutes(11).toDouble()) + SampoScorerBaseImpl.residentialScoreFunction(TimeUnit.HOURS.toMinutes(11).toDouble())
        // マーカーの影響力込みの自宅のスコア
        val houseScore = nestScore * (1 + sumOfGeometricSequence(SampoScorerBaseImpl.markerDefaultImpact, SampoScorerBaseImpl.markerImpactDecreasingRate, markerNum / 2 + markerNum % 2))
        // マーカーの影響力込みの会社のスコア
        val officeScore = nestScore * (1 + sumOfGeometricSequence(SampoScorerBaseImpl.markerDefaultImpact, SampoScorerBaseImpl.markerImpactDecreasingRate, markerNum / 2))
        // 自宅と会社の間の概算スコア
        val wayScore = SampoScorerBaseImpl.stayBonus + SampoScorerBaseImpl.transientScoreFunction(2.0) + SampoScorerBaseImpl.residentialScoreFunction(1.0)
        val expectedTotalScore = (wayScore * 58 + houseScore + officeScore) * SampoScorerBaseImpl.areaScoreFunction(salaryMan.territories.size.toDouble())
        // 17650 くらい
        assertThat(score, Matchers.closeTo(expectedTotalScore, expectedTotalScore * errorRate))
    }

}