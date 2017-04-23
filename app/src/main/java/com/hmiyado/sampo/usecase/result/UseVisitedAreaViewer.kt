package com.hmiyado.sampo.usecase.result

import com.hmiyado.sampo.domain.model.Area

/**
 * Created by hmiyado on 2017/04/23.
 */
interface UseVisitedAreaViewer {
    interface Sink {
        fun view(areas: Set<Area>)
    }
}