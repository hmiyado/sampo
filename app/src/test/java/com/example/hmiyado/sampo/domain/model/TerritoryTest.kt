package com.example.hmiyado.sampo.domain.model

import com.example.hmiyado.sampo.domain.math.Degree
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * Created by hmiyado on 2017/03/21.
 */
class TerritoryTest {
    @Test
    fun findLatitudeIdBy() {
        val minLatitude = Degree(-90)
        assertThat(Territory.findLatitudeIdBy(minLatitude), `is`(0))
        val maxLatitude = Degree(90)
        assertThat(Territory.findLatitudeIdBy(maxLatitude), `is`(Territory.DIVISION_NUMBER))
        val middleLatitude = Degree(0)
        assertThat(Territory.findLatitudeIdBy(middleLatitude), `is`(Territory.DIVISION_NUMBER / 2))
    }

    @Test
    fun findLongitudeIdBy() {
        val minLongitude = Degree(-180)
        assertThat(Territory.findLongitudeIdBy(minLongitude), `is`(0))
        val maxLongitude = Degree(180)
        assertThat(Territory.findLongitudeIdBy(maxLongitude), `is`(Territory.DIVISION_NUMBER))
        val middleLongitude = Degree(0)
        assertThat(Territory.findLongitudeIdBy(middleLongitude), `is`(Territory.DIVISION_NUMBER / 2))
    }

    @Test
    fun compareTo() {
        val scorer = TerritoryScorerSizeImpl
        val territoryValidPeriod = mock(TerritoryValidityPeriod::class.java)
        val territory0 = Territory(0, 0, emptyList(), scorer, territoryValidPeriod)
        val territory1 = Territory(0, 0, emptyList(), scorer, territoryValidPeriod)
        val territory2 = Territory(0, 1, emptyList(), scorer, territoryValidPeriod)
        val territory3 = Territory(1, 0, emptyList(), scorer, territoryValidPeriod)
        val territory4 = Territory(1, 1, emptyList(), scorer, territoryValidPeriod)
        assert(territory0 == territory1)
        assert(territory1 < territory2)
        assert(territory1 < territory3)
        assert(territory1 < territory4)
        assert(territory2 < territory3)
        assert(territory3 < territory4)
    }

}