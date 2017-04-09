package com.hmiyado.sampo.domain.model

import com.hmiyado.sampo.domain.math.Degree
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * Created by hmiyado on 2017/03/21.
 */
class TerritoryTest {
    @Test
    fun findLatitudeIdBy() {
        val minLatitude = Degree(-90)
        assertThat(Area.findLatitudeIdBy(minLatitude), `is`(0))
        val maxLatitude = Degree(90)
        assertThat(Area.findLatitudeIdBy(maxLatitude), `is`(Area.DIVISION_NUMBER))
        val middleLatitude = Degree(0)
        assertThat(Area.findLatitudeIdBy(middleLatitude), `is`(Area.DIVISION_NUMBER / 2))
    }

    @Test
    fun findLongitudeIdBy() {
        // 一周すると同じエリアになる
        assertThat(Area.findLongitudeIdBy(Degree(-180)), `is`(0))
        assertThat(Area.findLongitudeIdBy(Degree(180)), `is`(0))

        val maxLongitude = Degree(179.999999999999)
        assertThat(Area.findLongitudeIdBy(maxLongitude), `is`(Area.DIVISION_NUMBER - 1))
        val middleLongitude = Degree(0)
        assertThat(Area.findLongitudeIdBy(middleLongitude), `is`(Area.DIVISION_NUMBER / 2))
    }

    @Test
    fun compareTo() {
        val territory0 = Territory(locations = emptyList())
        val territory1 = Territory(locations = emptyList())
        val territory2 = Territory(locations = emptyList())
        val territory3 = Territory(locations = emptyList())
        val territory4 = Territory(locations = emptyList())
        assert(territory0 == territory1)
        assert(territory1 < territory2)
        assert(territory1 < territory3)
        assert(territory1 < territory4)
        assert(territory2 < territory3)
        assert(territory3 < territory4)
    }

}