package com.example.hmiyado.sampo.domain.model.mock

import com.example.hmiyado.sampo.domain.model.Location
import org.threeten.bp.Instant
import java.util.concurrent.TimeUnit

/**
 * Created by hmiyado on 2017/03/28.
 */
class LocationMockNeat : LocationMock {
    override val locations: List<Location>
        get() = generateLocations()

    fun generateLocations(): List<Location> {
        // 1週間の間，地点(0.0, 0.0) にいる
        val period = TimeUnit.DAYS.toMinutes(7)
        return (0..period).map { Location(0.0, 0.0, Instant.EPOCH.plusSeconds(TimeUnit.MINUTES.toSeconds(it))) }
    }
}