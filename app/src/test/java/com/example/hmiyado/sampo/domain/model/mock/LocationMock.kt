package com.example.hmiyado.sampo.domain.model.mock

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Territory

/**
 * Created by hmiyado on 2017/03/28.
 */
interface LocationMock {
    val territories: List<Territory>
    val locations: List<Location>
}