package com.example.hmiyado.sampo.domain.model.mock

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Marker
import com.example.hmiyado.sampo.domain.model.Territory

/**
 * Created by hmiyado on 2017/03/28.
 */
interface LocationMock {
    val markers: List<Marker>
    val territories: List<Territory>
    val locations: List<Location>
}