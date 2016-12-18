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

    // TODO map は変数ではなく，Observableの中にラップされた値としてもつように変更する
    // 変更に応じたSubjectからmapを取りまとめるSubjectに適宜更新後のmapを流すようにすればできるはず
    private var map: Map = Map(Location.Companion.empty(), Orientation(0f, 0f, 0f), 0f, 0f)

    private val onUpdateOriginalLocationSignal = PublishSubject<Map>()
    private val onUpdateOrientationSignal = PublishSubject<Map>()
    private val onUpdateScaleFactorSignal = PublishSubject<Map>()
    private val onUpdateRotateAngleSignal = PublishSubject<Map>()

    fun setOriginalLocation(originalLocation: Location) {
        map = Map.Builder(map).setOriginalLocation(originalLocation).build()
        onUpdateOriginalLocationSignal.onNext(map)
    }

    fun setOrientation(orientation: Orientation) {
        map = Map.Builder(map).setOrientation(orientation).build()
        onUpdateOrientationSignal.onNext(map)
    }

    fun setScaleFactor(scaleFactor: Float) {
        map = Map.Builder(map).setScaleFactor(scaleFactor).build()
        onUpdateScaleFactorSignal.onNext(map)
    }

    fun productScaleFactor(scaleFactor: Float) {
        setScaleFactor(scaleFactor * map.scaleFactor)
    }

    fun setRotateAngle(rotateAngle: Float) {
        map = Map.Builder(map).setRotateAngle(rotateAngle).build()
        onUpdateRotateAngleSignal.onNext(map)
    }

    fun addRotateAngle(rotateAngle: Float) {
        setRotateAngle(rotateAngle + map.rotateAngle)
    }

    fun getOnUpdateOriginalLocationSignal(): Observable<Map> {
        return onUpdateOriginalLocationSignal.share()
    }

    fun getOnUpdateOrientationSignal(): Observable<Map> {
        return onUpdateOrientationSignal.share()
    }

    fun getOnUpdateScaleFactorSignal(): Observable<Map> {
        return onUpdateScaleFactorSignal.share()
    }

    fun getOnUpdateRotateAngleSignal(): Observable<Map> {
        return onUpdateRotateAngleSignal.share()
    }

    fun getOnUpdateMapSignal(): Observable<Map> {
        return Observable.merge(
                getOnUpdateOriginalLocationSignal(),
                getOnUpdateRotateAngleSignal(),
                getOnUpdateOrientationSignal(),
                getOnUpdateScaleFactorSignal()
        )
    }

}