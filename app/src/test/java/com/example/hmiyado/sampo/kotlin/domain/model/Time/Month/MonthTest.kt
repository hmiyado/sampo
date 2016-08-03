package com.example.hmiyado.sampo.kotlin.domain.model.Time.Month

import com.example.hmiyado.sampo.kotlin.Month.Month
import com.example.hmiyado.sampo.kotlin.Month.MonthRange
import com.example.hmiyado.sampo.kotlin.Time.Day
import com.example.hmiyado.sampo.kotlin.Time.Second
import com.example.hmiyado.sampo.kotlin.Time.Year.Year
import org.junit.Test
import org.hamcrest.Matchers.*
import org.hamcrest.MatcherAssert.*

/**
 * Created by hmiyado on 2016/08/03.
 */
class MonthTest {

    @Test
    fun testNewInstance() {
        assertThat(
                "0年1月をインスタンス化",
                Month.January(Year(0)),
                `is`(Month.newInstance(1, Year(0)))
        )
        assertThat(
                "0年13月は1年1月になる",
                Month.January(Year(1)),
                `is`(Month.newInstance(13, Year(0)))
        )
        assertThat(
                "1年0月は0年12月になる",
                Month.December(Year(0)),
                `is`(Month.newInstance(0, Year(1)))
        )
        assertThat(
                "1年−1月は0年11月になる",
                Month.November(Year(0)),
                `is`(Month.newInstance(-1, Year(1)))
        )
    }

    @Test
    fun testRangeTo() {
        val actual: MonthRange = Month.January(Year(0)).rangeTo(Month.February(Year(0)))
        val expected: MonthRange = MonthRange(Month.January(Year(0)), Month.February(Year(0)))
        assertThat(
                "0年1月から0年2月のMonthRange",
                actual,
                equalTo(expected)
        )
    }

    @Test
    fun testPastSecondUntil() {
        val january = Month.January(Year(0))
        val february = Month.February(Year(0))

        assertThat(
                "0年1月から0年1月へ経過する秒数は0",
                january.pastSecondsUntil(january),
                `is`(Second(0))
        )
        assertThat(
                "0年1月から0年2月へ経過する秒数は31日分の秒数",
                january.pastSecondsUntil(february),
                `is`(Day(31).toSecond())
        )
        assertThat(
                "0年2月から0年1月へ経過する秒数は-31日分の秒数",
                february.pastSecondsUntil(january),
                `is`(Day(-31).toSecond())
        )
    }
}