package com.hmiyado.sampo.usecase

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import timber.log.Timber

/**
 * Created by hmiyado on 2017/03/22.
 */
class DefaultObserver<T>(
        private val onNext: (T) -> Unit,
        private val onError: (Throwable?) -> Unit = DefaultObserver.Companion::onErrorDefault,
        private val onComplete: () -> Unit = {},
        private val onSubscribe: (Disposable?) -> Unit = {}
) : Observer<T> {
    companion object {
        private fun onErrorDefault(e: Throwable?) {
            Timber.e(e, "raised unhandled error")
        }
    }

    override fun onError(e: Throwable?) {
        onError.invoke(e)
    }

    override fun onNext(t: T) {
        onNext.invoke(t)
    }

    override fun onComplete() {
        onComplete.invoke()
    }

    override fun onSubscribe(d: Disposable?) {
        onSubscribe.invoke(d)
    }
}