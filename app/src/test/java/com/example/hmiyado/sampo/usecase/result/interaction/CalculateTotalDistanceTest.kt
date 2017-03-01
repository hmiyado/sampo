package com.example.hmiyado.sampo.usecase.result.interaction

import com.example.hmiyado.sampo.domain.math.EuclidDistance
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.repository.location.LocationRepository
import com.example.hmiyado.sampo.usecase.result.UseTotalDistanceViewer
import org.junit.Test
import org.mockito.Mockito.*
import org.threeten.bp.Instant

/**
 * created by hmiyado on 2017/03/02.
 */
class CalculateTotalDistanceTest {

    @Test
    fun 距離を足し算できる() {
        val locationRepositoryMock = mock(LocationRepository::class.java)
        val measurement = EuclidDistance
        val useTotalDistanceViewer = mock(UseTotalDistanceViewer.Sink::class.java)

        `when`(locationRepositoryMock.loadLocationList()).thenReturn(listOf(
                Location(1.0, 0.0, Instant.EPOCH),
                Location(2.0, 0.0, Instant.EPOCH),
                Location(3.0, 0.0, Instant.EPOCH)
        ))

        CalculateTotalDistance(locationRepositoryMock, measurement, useTotalDistanceViewer)

        verify(useTotalDistanceViewer).show(2.0)
    }

    @Test
    fun 順不同で距離を足し算できる() {
        val locationRepositoryMock = mock(LocationRepository::class.java)
        val measurement = EuclidDistance
        val useTotalDistanceViewer = mock(UseTotalDistanceViewer.Sink::class.java)

        `when`(locationRepositoryMock.loadLocationList()).thenReturn(listOf(
                Location(2.0, 0.0, Instant.EPOCH.plusSeconds(1)),
                Location(1.0, 0.0, Instant.EPOCH),
                Location(3.0, 0.0, Instant.EPOCH.plusSeconds(2))
        ))

        CalculateTotalDistance(locationRepositoryMock, measurement, useTotalDistanceViewer)

        verify(useTotalDistanceViewer).show(2.0)
    }

    @Test
    fun emptyがあってもバグらない() {
        val locationRepositoryMock = mock(LocationRepository::class.java)
        val measurement = EuclidDistance
        val useTotalDistanceViewer = mock(UseTotalDistanceViewer.Sink::class.java)

        `when`(locationRepositoryMock.loadLocationList()).thenReturn(listOf(
                Location(2.0, 0.0, Instant.EPOCH.plusSeconds(1)),
                Location.empty(),
                Location(3.0, 0.0, Instant.EPOCH.plusSeconds(2))
        ))

        CalculateTotalDistance(locationRepositoryMock, measurement, useTotalDistanceViewer)

        verify(useTotalDistanceViewer).show(1.0)
    }
}