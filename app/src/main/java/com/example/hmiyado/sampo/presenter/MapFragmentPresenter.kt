package com.example.hmiyado.sampo.presenter

import com.example.hmiyado.sampo.domain.usecase.map.UseMapViewerInteraction
import com.example.hmiyado.sampo.view.MapFragment

/**
 * Created by hmiyado on 2016/07/26.
 * 地図フラグメントのプレゼンター
 */
class MapFragmentPresenter(
        private val mapFragment: MapFragment
) {
    fun onViewCreated() {
        UseMapViewerInteraction.create(mapFragment.mapViewPresenter, mapFragment.mapViewController)
    }
}