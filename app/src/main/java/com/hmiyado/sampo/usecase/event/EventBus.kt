package com.hmiyado.sampo.usecase.event

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by hmiyado on 2017/04/22.
 */
object EventBus {
    private val eventSubject = PublishSubject.create<Event>()

    fun dispatch(event: Event) {
        eventSubject.onNext(event)
    }

    val onEvent: Observable<Event>
        get() = eventSubject.hide().share()
}