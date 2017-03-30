package com.example.hmiyado.sampo.domain.model.mock

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Territory
import com.example.hmiyado.sampo.usecase.map.interaction.UpdateTerritory
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.threeten.bp.Instant
import java.util.concurrent.TimeUnit

/**
 * Created by hmiyado on 2017/03/28.
 */
class LocationMockNeat(
        private val days: Long = 1
) : LocationMock {
    override val territories: List<Territory>
        get() = generateTerritories()
    override val locations: List<Location>
        get() = generateLocations()

    fun generateTerritories(): List<Territory> {
        return locations.fold(emptyList<Territory>(), { territories, location -> UpdateTerritory.updateTerritories(location, territories) })
    }

    fun generateLocations(): List<Location> {
        // 1週間の間，地点(0.0, 0.0) にいる
        val period = TimeUnit.DAYS.toMinutes(days)
        return (0..period).map { Location(0.0, 0.0, Instant.EPOCH.plusSeconds(TimeUnit.MINUTES.toSeconds(it))) }
    }

    @Test
    fun assertOneTerritory() {
        assertThat(territories.size, `is`(1))
    }
}