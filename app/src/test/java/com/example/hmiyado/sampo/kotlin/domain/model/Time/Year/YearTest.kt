package com.example.hmiyado.sampo.kotlin.domain.model.Time.Year

import com.example.hmiyado.sampo.kotlin.Time.Day
import com.example.hmiyado.sampo.kotlin.Time.Second
import com.example.hmiyado.sampo.kotlin.Time.Year.Year
import org.junit.Test
import org.hamcrest.Matchers.*
import org.junit.Assert.*

/**
 * Created by hmiyado on 2016/08/03.
 */
class YearTest {

    @Test
    fun testPastSecondsUntil() {
        val year1 = Year(1)
        val year2 = Year(2)
        assertThat(
                "1年から1年の間に経過する秒数は0",
                year1.pastSecondsUntil(year1),
                `is`(Second(0))
        )
        assertThat(
                "1年から2年の間に経過する秒数は365日分",
                year1.pastSecondsUntil(year2),
                `is`(Day(365).toSecond())
        )
        assertThat(
                "2年から1年の間に経過する秒数は-365日分",
                year2.pastSecondsUntil(year1),
                `is`(Day(-365).toSecond())
        )
    }
}