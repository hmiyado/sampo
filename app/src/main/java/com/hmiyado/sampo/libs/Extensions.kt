package com.hmiyado.sampo.libs

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by hmiyado on 2017/01/05.
 *
 * ライブラリの拡張関数をおく
 */

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    this.add(disposable)
}