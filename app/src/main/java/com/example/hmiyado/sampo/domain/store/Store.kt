package com.example.hmiyado.sampo.domain.store

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Map
import com.example.hmiyado.sampo.domain.model.Orientation
import rx.Observable
import rx.lang.kotlin.PublishSubject

/**
 * Created by hmiyado on 2016/12/15.

 * 状態の保持/更新方法の提供/状態の提供の責務を負うクラス．
 */
class Store {

    private var map: Map = Map(Location.Companion.empty(), Orientation(0f, 0f, 0f), 0f, 0f)

    private val mapSubject = PublishSubject<Map>()

    private val onUpdateOriginalLocationSignal = PublishSubject<Location>()
    private val onUpdateOrientationSignal = PublishSubject<Orientation>()
    private val onUpdateScaleFactorSignal = PublishSubject<Float>()
    private val onUpdateRotateAngleSignal = PublishSubject<Float>()

    init {
        mapSubject.onNext(Map.empty())
    }

    private fun getMapSignalUpdatedOriginalLocation(): Observable<Map> {
        return onUpdateOriginalLocationSignal
                .withLatestFrom(mapSubject, { location, map ->
                    Map.Builder(map).setOriginalLocation(location).build()
                })
                .share()
    }

    private fun getMapSignalUpdatedOrientation(): Observable<Map> {
        return onUpdateOrientationSignal
                .withLatestFrom(mapSubject, { orientation, map ->
                    Map.Builder(map).setOrientation(orientation).build()
                })
                .share()
    }

    private fun getMapSignalUpdatedScaleFactor(): Observable<Map> {
        return onUpdateScaleFactorSignal
                .withLatestFrom(mapSubject, { scaleFactor, map ->
                    Map.Builder(map).setScaleFactor(scaleFactor).build()
                })
                .share()
    }

    private fun getMapSignalUpdatedRotateAngle(): Observable<Map> {
        return onUpdateScaleFactorSignal
                .withLatestFrom(mapSubject, { rotateAngle, map ->
                    Map.Builder(map).setRotateAngle(rotateAngle).build()
                })
                .share()
    }

    fun setOriginalLocation(originalLocation: Location) {
        onUpdateOriginalLocationSignal.onNext(originalLocation)
    }

    fun setOrientation(orientation: Orientation) {
        onUpdateOrientationSignal.onNext(orientation)
    }

    fun setScaleFactor(scaleFactor: Float) {
        onUpdateScaleFactorSignal.onNext(scaleFactor)
    }

    fun productScaleFactor(scaleFactor: Float) {
        setScaleFactor(scaleFactor * map.scaleFactor)
    }

    fun setRotateAngle(rotateAngle: Float) {
        onUpdateRotateAngleSignal.onNext(rotateAngle)
    }

    fun addRotateAngle(rotateAngle: Float) {
        setRotateAngle(rotateAngle + map.rotateAngle)
    }

    fun getMapSignal(): Observable<Map> {
        return Observable.merge(
                getMapSignalUpdatedOriginalLocation(),
                getMapSignalUpdatedOrientation(),
                getMapSignalUpdatedScaleFactor(),
                getMapSignalUpdatedRotateAngle()
        )
    }

}