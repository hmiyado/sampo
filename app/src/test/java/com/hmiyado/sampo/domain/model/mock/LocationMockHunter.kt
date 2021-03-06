package com.hmiyado.sampo.domain.model.mock

import com.hmiyado.sampo.domain.model.Area
import com.hmiyado.sampo.domain.model.Location
import com.hmiyado.sampo.domain.model.Marker
import com.hmiyado.sampo.domain.model.Territory
import com.hmiyado.sampo.usecase.map.interaction.UpdateTerritory
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.threeten.bp.Instant
import java.util.concurrent.TimeUnit

/**
 * Created by hmiyado on 2017/03/31.
 */
class LocationMockHunter(
        val days: Long = 1,
        markerNum: Int = 3
) : LocationMock {
    override val markers: List<Marker> = (1..markerNum).map {
        Marker(
                Location(
                        Area.LATITUDE_UNIT * it,
                        Area.LONGITUDE_UNIT * it
                )
        )
    }
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
        return (0..period).map {
            Location(
                    Area.LATITUDE_UNIT * it,
                    Area.LONGITUDE_UNIT * it,
                    Instant.EPOCH.plusSeconds(TimeUnit.MINUTES.toSeconds(it))
            )
        }
    }

    @Test
    fun assertOneTerritory() {
        MatcherAssert.assertThat(territories.size.toLong(), CoreMatchers.`is`(TimeUnit.DAYS.toMinutes(days)))
    }

}