package com.example.hmiyado.sampo.repository.compass

import com.example.hmiyado.sampo.domain.math.toDegree
import com.example.hmiyado.sampo.domain.model.Orientation
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * Created by hmiyado on 2016/12/07.
 *
 * 仮想的な方位を取得するサービス．
 */
class CompassSensorVirtualImpl : CompassSensor {
    private val virtualCompassSubject = PublishSubject.create<Orientation>()
    private val timerObservable = Observable.interval(5, TimeUnit.SECONDS)
    private var timerSubscription: Disposable = timerObservable.subscribe()
    private var nextOrientation = Orientation.empty()

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
        val diff = Math.PI / 16
        nextOrientation = Orientation(
                nextOrientation.azimuth + diff.toDegree(),
                nextOrientation.pitch + diff.toDegree(),
                nextOrientation.roll + diff.toDegree()
        )
    }

    override fun stopCompassService() {
        timerSubscription.dispose()
    }
}