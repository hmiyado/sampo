package com.example.hmiyado.sampo.usecase.map.mapview

import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.model.Location

/**
 * Created by hmiyado on 2016/12/10.
 *
 * 地図閲覧への出力を責務とする．
 *
 * 地図を閲覧するのに必要な情報
 * - 地図の見た目
 *  - 縮尺
 *  - 回転角
 * - 位置情報
 *  - 現在地
 *  - その他の地点情報
 */
interface UseMapViewSink {
    data class DrawableMap(
            val originalLocation: Location,
            val scaleFactor: Float,
            val rotateAngle: Degree,
            val footmarks: List<Location>
    )

    fun draw(drawableMap: DrawableMap)
}

