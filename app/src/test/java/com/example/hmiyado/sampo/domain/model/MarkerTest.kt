package com.example.hmiyado.sampo.domain.model

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.threeten.bp.Instant
import org.threeten.bp.Period
import java.util.concurrent.TimeUnit

class MarkerTest {
    @Test
    fun getScore() {
        val marker = Marker(
                location = Location(),
                validityPeriod = ValidityPeriod.create(
                        start = Instant.EPOCH,
                        period = Period.ofDays(1)
                ),
                defaultScore = 100.0
        )

        assertThat(marker.getScore(Instant.EPOCH), `is`(100.0))
        assertThat(marker.getScore(Instant.EPOCH.plus(Period.ofDays(1))), `is`(0.0))
        assertThat(marker.getScore(Instant.EPOCH.plusMillis(TimeUnit.HOURS.toMillis(12))), `is`(50.0))
    }

}