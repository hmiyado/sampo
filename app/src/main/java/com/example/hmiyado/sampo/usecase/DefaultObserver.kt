package com.example.hmiyado.sampo.usecase

import rx.Observer
import timber.log.Timber

/**
 * Created by hmiyado on 2017/03/22.
 */
class DefaultObserver<T>(
        private val onNext: (T) -> Unit,
        private val onError: (Throwable?) -> Unit,
        private val onCompleted: () -> Unit
) : Observer<T> {
    companion object {
        private fun onErrorDefault(e: Throwable?) {
            Timber.e(e, "raised unhandled error")
        }
    }

    constructor(onNext: (T) -> Unit) : this(onNext, onError = DefaultObserver.Companion::onErrorDefault, onCompleted = {})

    override fun onError(e: Throwable?) {
        onError.invoke(e)
    }

    override fun onCompleted() {
        onCompleted.invoke()
    }

    override fun onNext(t: T) {
        onNext.invoke(t)
    }

}