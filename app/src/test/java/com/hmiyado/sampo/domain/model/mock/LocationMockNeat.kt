package com.hmiyado.sampo.domain.model.mock

import com.hmiyado.sampo.domain.model.Location
import com.hmiyado.sampo.domain.model.Marker
import com.hmiyado.sampo.domain.model.Territory
import com.hmiyado.sampo.usecase.map.interaction.UpdateTerritory
import org.threeten.bp.Instant
import java.util.concurrent.TimeUnit

/**
 * Created by hmiyado on 2017/03/28.
 */
class LocationMockNeat(
        val days: Long = 1,
        markerNum: Long = 3
) : LocationMock {
    override val markers: List<Marker> = (1..markerNum).map { Marker(Location()) }
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
}