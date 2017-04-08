package com.example.hmiyado.sampo.repository.marker

import com.example.hmiyado.sampo.domain.model.Marker

/**
 * Created by hmiyado on 2017/04/08.
 */
interface MarkerRepository {
    fun saveMarker(marker: Marker)
    fun saveMarkers(markers: List<Marker>)
    fun loadMarkers(): List<Marker>
}