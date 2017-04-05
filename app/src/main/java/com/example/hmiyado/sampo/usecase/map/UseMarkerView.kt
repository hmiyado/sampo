package com.example.hmiyado.sampo.usecase.map

import com.example.hmiyado.sampo.domain.model.Marker
import io.reactivex.Observable

/**
 * Created by hmiyado on 2017/04/04.
 */
interface UseMarkerView {
    interface Source {
        val onTouchSignal: Observable<Unit>
    }

    interface Sink {
        fun draw(markers: List<Marker>)
    }
}