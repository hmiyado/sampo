package com.example.hmiyado.sampo.usecase.map.compassview

import com.example.hmiyado.sampo.domain.model.Orientation

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 方位磁針への出力
 */
interface UseCompassViewSink {
    fun draw(orientation: Orientation)
}