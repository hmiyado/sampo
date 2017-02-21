package com.example.hmiyado.sampo.domain.store

import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Orientation
import rx.Observable
import rx.lang.kotlin.BehaviorSubject

/**
 * Created by hmiyado on 2016/12/15.

 * 状態の保持/更新方法の提供/状態の提供の責務を負うクラス．
 */
object MapStore {
    /**
     * 倍率１のときの，1 dip あたりの地図上の距離（メートル）
     */
    val SCALE_UNIT = 1

    private val originalLocationSubject = BehaviorSubject<Location>(Location.empty())
    private val orientationSubject = BehaviorSubject<Orientation>(Orientation.empty())
    private val scaleFactorSubject = BehaviorSubject<Float>(1f)
    private val rotateAngleSubject = BehaviorSubject<Degree>(Degree(0.0))
    private val footmarksSubject = BehaviorSubject<List<Location>>(emptyList())

    init {
        originalLocationSubject
                .subscribe { footmarksSubject.onNext(footmarksSubject.value + it) }
    }

    fun setOriginalLocation(originalLocation: Location) {
        originalLocationSubject.onNext(originalLocation)
    }

    fun setOrientation(orientation: Orientation) {
        orientationSubject.onNext(orientation)
    }

    fun setScaleFactor(scaleFactor: Float) {
        scaleFactorSubject.onNext(scaleFactorSubject.value * scaleFactor)
    }

    fun setRotateAngle(rotateAngle: Degree) {
        rotateAngleSubject.onNext(rotateAngleSubject.value + rotateAngle)
    }

    fun getOriginalLocation(): Observable<Location> = originalLocationSubject.asObservable().share()

    fun getOrientation(): Observable<Orientation> = orientationSubject.asObservable().share()

    fun getScaleFactor(): Observable<Float> = scaleFactorSubject.asObservable().share()

    fun getRotateAngle(): Observable<Degree> = rotateAngleSubject.asObservable().share()

    fun getFootmarks(): Observable<List<Location>> = footmarksSubject.asObservable().share()
}