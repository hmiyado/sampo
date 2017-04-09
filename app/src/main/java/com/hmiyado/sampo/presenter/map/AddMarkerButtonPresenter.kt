package com.hmiyado.sampo.presenter.map

import android.support.design.widget.FloatingActionButton
import com.hmiyado.sampo.presenter.ViewPresenter
import com.hmiyado.sampo.usecase.map.UseMarkerAdder
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by hmiyado on 2017/04/06.
 */
class AddMarkerButtonPresenter(
        view: FloatingActionButton
) : ViewPresenter<FloatingActionButton>(view), UseMarkerAdder.Source {
    override val onClickedSignal: Observable<Unit> = RxView
            .clicks(view)
            .subscribeOn(AndroidSchedulers.mainThread())
            .map { Unit }
            .share()
}