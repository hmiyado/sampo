package com.hmiyado.sampo.domain.model.mock

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * Created by hmiyado on 2017/04/01.
 */
class LocationMockTest {
    @Test
    fun testNeat() {
        val neat = LocationMockNeat()
        assertThat(neat.locations.size.toLong(), `is`(TimeUnit.DAYS.toMinutes(neat.days) + 1))
        assertThat(neat.territories.size, `is`(1))
    }

    @Test
    fun testHunter() {
        val hunter = LocationMockHunter()
        assertThat(hunter.locations.size.toLong(), `is`(TimeUnit.DAYS.toMinutes(hunter.days) + 1))
        assertThat(hunter.territories.size.toLong(), `is`(TimeUnit.DAYS.toMinutes(hunter.days) + 1))
        hunter.territories.forEach {
            assertThat(it.locations.size, `is`(1))
        }

    }

}