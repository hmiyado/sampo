package com.hmiyado.sampo.domain.model.mock

import com.hmiyado.sampo.domain.model.Area
import com.hmiyado.sampo.domain.model.Location
import com.hmiyado.sampo.domain.model.Marker
import com.hmiyado.sampo.domain.model.Territory
import com.hmiyado.sampo.usecase.map.interaction.UpdateTerritory
import org.threeten.bp.Instant
import java.util.concurrent.TimeUnit

/**
 * Created by hmiyado on 2017/04/01.
 *
 * 自宅と職場を往復し続けるサラリーマンをシミュレーションするモック．
 * 自宅に11時間いて，1時間かけて会社へ行き，11時間会社にいて，1時間かけて自宅に戻ってくる．
 * 会社との往復は，1地点あたり11Territoryを通ってくる（つまり，58地点，58領域を通ってくる．1時間の内両端は自宅・会社なので2点少ない）．
 */
class LocationMockSalaryMan(
        val days: Long = 1,
        val markerNum: Long = 2
) : LocationMock {
    override val markers: List<Marker>
        get() = generateMarkers()
    override val territories: List<Territory>
        get() = generateTerritories()
    override val locations: List<Location>
        get() = generateLocations()

    fun generateMarkers(): List<Marker> {
        return (1..markerNum).map {
            if (it % 2 == 0L) {
                Marker()
            } else {
                val time = TimeUnit.HOURS.toMinutes(1)
                Marker(
                        Location(
                                Area.LATITUDE_UNIT * time,
                                Area.LONGITUDE_UNIT * time
                        )
                )
            }
        }
    }

    fun generateTerritories(): List<Territory> {
        return locations.fold(emptyList<Territory>(), { territories, location -> UpdateTerritory.updateTerritories(location, territories) })
    }

    fun generateLocations(): List<Location> {
        return (1..days)
                .map {
                    Instant.EPOCH.plusSeconds(TimeUnit.DAYS.toMinutes(it - 1) * 60)
                }
                .flatMap {
                    generateLocationsPerDay(it)
                }
    }

    fun generateLocationsPerDay(timeOffset: Instant): List<Location> {
        val dayPeriod = TimeUnit.DAYS.toMinutes(1)
        return (0..dayPeriod).map {
            when {
                it <= TimeUnit.HOURS.toMinutes(11)                                       -> {
                    Location(0.0, 0.0, timeOffset.plusSeconds(it * 60))
                }
                it > TimeUnit.HOURS.toMinutes(11) && it < TimeUnit.HOURS.toMinutes(12)   -> {
                    val timeToMove = it - TimeUnit.HOURS.toMinutes(11)
                    Location(
                            Area.LATITUDE_UNIT * timeToMove,
                            Area.LONGITUDE_UNIT * timeToMove,
                            timeOffset.plusSeconds(it * 60)
                    )
                }
                it >= TimeUnit.HOURS.toMinutes(12) && it <= TimeUnit.HOURS.toMinutes(23) -> {
                    val time = TimeUnit.HOURS.toMinutes(1)
                    Location(
                            Area.LATITUDE_UNIT * time,
                            Area.LONGITUDE_UNIT * time,
                            timeOffset.plusSeconds(it * 60)
                    )
                }
                else                                                                     -> {
                    val time = TimeUnit.HOURS.toMinutes(1) - (it - TimeUnit.HOURS.toMinutes(23))
                    Location(
                            Area.LATITUDE_UNIT * time,
                            Area.LONGITUDE_UNIT * time,
                            timeOffset.plusSeconds(it * 60)
                    )
                }
            }
        }
    }
}