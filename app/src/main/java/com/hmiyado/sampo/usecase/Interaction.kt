package com.hmiyado.sampo.usecase

import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by hmiyado on 2017/03/22.
 */
abstract class Interaction<T> {
    protected abstract fun buildProducer(): Observable<T>
    protected abstract fun buildConsumer(): Observer<T>

    class Builder<T>(
            var lifecycleProvider: LifecycleProvider<T>,
            var event: T
    ) {
        var observeThread: Scheduler = AndroidSchedulers.mainThread()
        var subscribeThread: Scheduler = Schedulers.newThread()

        fun <T> build(interaction: Interaction<T>) {
            interaction.buildProducer()
                    .subscribeOn(subscribeThread)
                    .observeOn(observeThread)
                    .compose(lifecycleProvider.bindUntilEvent(event))
                    .subscribe(interaction.buildConsumer())
        }

        fun buildAll(interactions: List<Interaction<*>>) {
            interactions.forEach { build(it) }
        }
    }
}