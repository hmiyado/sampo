package com.example.hmiyado.sampo.repository.compass

import com.example.hmiyado.sampo.domain.model.Orientation
import rx.Observable
import rx.Subscription
import rx.lang.kotlin.PublishSubject
import rx.subjects.Subject
import java.util.concurrent.TimeUnit

/**
 * Created by hmiyado on 2016/12/07.
 *
 * 仮想的な方位を取得するサービス．
 */
class CompassServiceVirtualImpl : CompassService {
    private val virtualCompassSubject: Subject<Orientation, Orientation> = PublishSubject<Orientation>()
    private val timerObservable = Observable.interval(5, TimeUnit.SECONDS)
    private var timerSubscription: Subscription = timerObservable.subscribe()
    private var nextOrientation = Orientation(0f, 0f, 0f)

    override fun getCompassService(): Observable<Orientation> {
        return virtualCompassSubject.share()
    }

    override fun startCompassService() {
        timerSubscription = timerObservable.subscribe {
            virtualCompassSubject.onNext(nextOrientation)
            updateNextOrientation()
        }
    }

    private fun updateNextOrientation() {
        val diff = (Math.PI / 16).toFloat()
        nextOrientation = Orientation(
                nextOrientation.azimuth + diff,
                nextOrientation.pitch + diff,
                nextOrientation.roll + diff
        )
    }

    override fun stopCompassService() {
        timerSubscription.unsubscribe()
    }
}