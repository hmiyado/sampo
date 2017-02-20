package com.example.hmiyado.sampo.domain.store

import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Map
import com.example.hmiyado.sampo.domain.model.Orientation
import rx.Observable
import rx.lang.kotlin.PublishSubject
import timber.log.Timber

/**
 * Created by hmiyado on 2016/12/15.

 * 状態の保持/更新方法の提供/状態の提供の責務を負うクラス．
 */
object Store {

    private var map: Map = Map.empty()

    //    private val mapSubject = PublishSubject<Map>()

    private val onUpdateOriginalLocationSignal = PublishSubject<Location>()
    private val onUpdateOrientationSignal = PublishSubject<Orientation>()
    private val onUpdateScaleFactorSignal = PublishSubject<Float>()
    private val onUpdateRotateAngleSignal = PublishSubject<Degree>()

    init {
        //        mapSubject.subscribe()
        //        mapSubject.onNext(Map.empty())
    }

    private fun getMapSignalUpdatedOriginalLocation(): Observable<Map> {
        return onUpdateOriginalLocationSignal
                .map {
                    Map.Builder(map).setOriginalLocation(it).addFootmark(map.originalLocation).build()
                }
                //                .withLatestFrom(mapSubject, { location, map ->
                //                    Map.Builder(map).setOriginalLocation(location).build()
                //                })
                .share()
    }

    private fun getMapSignalUpdatedOrientation(): Observable<Map> {
        return onUpdateOrientationSignal
                .map {
                    Map.Builder(map).setOrientation(it).build()
                }
                //                .withLatestFrom(mapSubject, { orientation, map ->
                //                    Map.Builder(map).setOrientation(orientation).build()
                //                })
                .share()
    }

    private fun getMapSignalUpdatedScaleFactor(): Observable<Map> {
        return onUpdateScaleFactorSignal
                .doOnNext { Timber.d("update scale factor by $it") }
                .map {
                    Map.Builder(map).setScaleFactor(map.scaleFactor * it).build()
                }
                //                .withLatestFrom(mapSubject, { scaleFactor, map ->
                //                    Timber.d("scaleFactor=$scaleFactor")
                //                    Map.Builder(map).setScaleFactor(scaleFactor).build()
                //                })
                .share()
    }

    private fun getMapSignalUpdatedRotateAngle(): Observable<Map> {
        return onUpdateRotateAngleSignal
                .map {
                    Map.Builder(map).setRotateAngle(map.rotateAngle + it).build()
                }
                //                .withLatestFrom(mapSubject, { rotateAngle, map ->
                //                    Map.Builder(map).setRotateAngle(rotateAngle).build()
                //                })
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
        setScaleFactor(scaleFactor)
    }

    fun setRotateAngle(rotateAngle: Degree) {
        onUpdateRotateAngleSignal.onNext(rotateAngle)
    }

    fun addRotateAngle(rotateAngle: Degree) {
        setRotateAngle(rotateAngle)
    }

    fun getMapSignal(): Observable<Map> {
        return Observable.merge(
                getMapSignalUpdatedOriginalLocation(),
                getMapSignalUpdatedOrientation(),
                getMapSignalUpdatedScaleFactor(),
                getMapSignalUpdatedRotateAngle()
        )
                .onBackpressureDrop()
                .doOnNext { map = it }
                .share()
    }

}