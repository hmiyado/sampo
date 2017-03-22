package com.example.hmiyado.sampo.usecase.map.store

import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Orientation
import com.example.hmiyado.sampo.domain.model.Territory
import com.example.hmiyado.sampo.domain.model.TerritoryValidityPeriod
import rx.Observable
import rx.lang.kotlin.BehaviorSubject

/**
 * Created by hmiyado on 2016/12/15.

 * 状態の保持/更新方法の提供/状態の提供の責務を負うクラス．
 */
class MapStoreImpl : MapStore {
    private val originalLocationSubject = BehaviorSubject<Location>(Location.Companion.empty())
    private val orientationSubject = BehaviorSubject<Orientation>(Orientation.Companion.empty())
    private val scaleFactorSubject = BehaviorSubject<Float>(1f)
    private val rotateAngleSubject = BehaviorSubject<Degree>(Degree(0.0))
    private val footmarksSubject = BehaviorSubject<List<Location>>(emptyList())
    private val territoriesSubject = BehaviorSubject<List<Territory>>(emptyList())
    private val territoryValidityPeriodSubject = BehaviorSubject<TerritoryValidityPeriod>()

    init {
        originalLocationSubject
                .filter { it != Location.empty() }
                .subscribe { footmarksSubject.onNext(footmarksSubject.value + it) }
    }

    override fun setOriginalLocation(originalLocation: Location) {
        originalLocationSubject.onNext(originalLocation)
    }

    override fun setOrientation(orientation: Orientation) {
        orientationSubject.onNext(orientation)
    }

    override fun setScaleFactor(scaleFactor: Float) {
        scaleFactorSubject.onNext(scaleFactorSubject.value * scaleFactor)
    }

    override fun setRotateAngle(rotateAngle: Degree) {
        rotateAngleSubject.onNext(rotateAngleSubject.value + rotateAngle)
    }

    override fun setTerritoryValidityPeriod(territoryValidityPeriod: TerritoryValidityPeriod) {
        territoryValidityPeriodSubject.onNext(territoryValidityPeriod)
    }

    override fun setTerritories(territories: List<Territory>) {
        territoriesSubject.onNext(territories)
    }

    override fun getOriginalLocation(): Observable<Location> = originalLocationSubject.asObservable().share()

    override fun getOrientation(): Observable<Orientation> = orientationSubject.asObservable().share()

    override fun getScaleFactor(): Observable<Float> = scaleFactorSubject.asObservable().share()

    override fun getRotateAngle(): Observable<Degree> = rotateAngleSubject.asObservable().share()

    override fun getFootmarks(): Observable<List<Location>> = footmarksSubject.asObservable().share()

    override fun getTerritoryValidityPeriod(): Observable<TerritoryValidityPeriod> = territoryValidityPeriodSubject.asObservable().share()

    override fun getTerritories(): Observable<List<Territory>> = territoriesSubject.asObservable().share()
}