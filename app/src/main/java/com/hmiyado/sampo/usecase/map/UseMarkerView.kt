package com.hmiyado.sampo.usecase.map

import com.hmiyado.sampo.domain.model.DrawableMap
import com.hmiyado.sampo.domain.model.Marker
import io.reactivex.Observable

/**
 * Created by hmiyado on 2017/04/04.
 */
interface UseMarkerView {
    interface Source {
        val onTouchSignal: Observable<Unit>
    }

    interface Sink {
        data class DrawableMarkers(
                val drawableMap: DrawableMap,
                val markers: List<Marker>
        )

        fun draw(drawableMarkers: DrawableMarkers)
    }
}