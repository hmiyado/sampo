package com.hmiyado.sampo.usecase.map

import com.hmiyado.sampo.domain.model.Orientation

/**
 * Created by hmiyado on 2017/02/28.
 *
 * 方位磁針の View の interface
 */
interface UseCompassView {
    interface Sink {
        fun draw(orientation: Orientation)
    }

    interface Source
}