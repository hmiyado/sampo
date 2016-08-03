package com.example.hmiyado.sampo.kotlin.domain.model.Time

import com.example.hmiyado.sampo.kotlin.Month.Month
import com.example.hmiyado.sampo.kotlin.Time.*
import com.example.hmiyado.sampo.kotlin.Time.Year.Year
import org.junit.Assert.*
import org.junit.Test
import org.hamcrest.Matchers.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by hmiyado on 2016/07/31.
 */

class LocalDateTimeTest {

    @Test
    fun testPlusTime() {
        val localDateTime = LocalDateTime.Companion.Factory.init().complete()
        assertEquals(
                LocalDateTime.Companion.Factory.init().build(Second(1)).complete(),
                localDateTime + Second(1)
        )
        assertEquals(
                LocalDateTime.Companion.Factory.init().build(Minute(1)).build(Second(0)).complete(),
                localDateTime + Second(60)
        )
    }

    @Test
    fun testMinusTime() {
        val localDateTime = LocalDateTime.Companion.Factory.init().complete()
        assertEquals(
                LocalDateTime.Companion.Factory
                        .init()
                        .build(Month.newInstance(12, Year(1969)))
                        .build(Day(31))
                        .build(Hour(23))
                        .build(Minute(59))
                        .build(Second(59))
                        .complete(),
                localDateTime - Second(1)
        )
    }

    @Test
    fun testCompareTo() {
        val localDateTime = LocalDateTime.Companion.Factory.init().complete()
        assert(localDateTime == localDateTime)
        assertThat(
                "1秒前より1秒後のほうが大きい",
                localDateTime,
                greaterThan(localDateTime - Second(1))
        )
    }

    @Test
    fun testToUnixTime() {
        assertThat(
                LocalDateTime.UnixEpoch.toUnixTime(),
                `is`(Second(0))
        )
        assertThat(
                LocalDateTime.UnixEpoch,
                `is`(LocalDateTime.Companion.Factory.initByUnixTime(Second(0)).complete())
        )
        assertThat(
                LocalDateTime.Companion.Factory
                        .init()
                        .build(Month.Companion.newInstance(1, Year(1970)))
                        .build(Day(1))
                        .build(Hour(0))
                        .build(Minute(0))
                        .build(Second(30))
                        .complete().toUnixTime(),
                `is`(LocalDateTime.UnixEpoch.toUnixTime() + Second(30))
        )

    }
}