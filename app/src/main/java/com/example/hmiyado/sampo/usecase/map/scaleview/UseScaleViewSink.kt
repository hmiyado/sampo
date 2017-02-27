package com.example.hmiyado.sampo.usecase.map.scaleview

/**
 * Created by hmiyado on 2016/12/24.
 *
 * 縮尺のビューを描画する人
 */
interface UseScaleViewSink {

    fun draw(): Unit

    fun setScale(scale: Float): Unit
}