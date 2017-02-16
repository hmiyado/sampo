package com.example.hmiyado.sampo.usecase.map.compassview

import android.graphics.Canvas
import com.example.hmiyado.sampo.presenter.map.CompassViewPresenter
import rx.Observable

/**
 * Created by hmiyado on 2016/12/21.
 *
 * 方位磁針からの入力を扱う
 */
class UseCompassViewInput(
        private val compassViewPresenter: CompassViewPresenter
) {
    fun getOnDrawSignal(): Observable<Canvas> = compassViewPresenter.getOnDrawSignal()
}