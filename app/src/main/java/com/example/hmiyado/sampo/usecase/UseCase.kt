package com.example.hmiyado.sampo.usecase

import rx.Observable
import rx.Observer

/**
 * Created by hmiyado on 2017/03/22.
 */
abstract class UseCase<T> : Interaction() {
    abstract fun buildProducer(): Observable<T>
    abstract fun buildConsumer(): Observer<T>
}