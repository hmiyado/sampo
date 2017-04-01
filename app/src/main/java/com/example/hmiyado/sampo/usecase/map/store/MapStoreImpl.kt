package com.example.hmiyado.sampo.usecase.map.store

import com.example.hmiyado.sampo.domain.math.Degree
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Orientation
import com.example.hmiyado.sampo.domain.model.Territory
import com.example.hmiyado.sampo.domain.model.ValidityPeriod
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.threeten.bp.Instant
import org.threeten.bp.Period
import timber.log.Timber

/**
 * Created by hmiyado on 2016/12/15.

 * 状態の保持/更新方法の提供/状態の提供の責務を負うクラス．
 */
class MapStoreImpl : MapStore {
    private val originalLocationSubject = BehaviorSubject.createDefault<Location>(Location.empty())
    private val orientationSubject = BehaviorSubject.createDefault<Orientation>(Orientation.Companion.empty())
    private val scaleFactorSubject = BehaviorSubject.createDefault<Float>(1f)
    private val rotateAngleSubject = BehaviorSubject.createDefault<Degree>(Degree(0.0))
    private val footmarksSubject = BehaviorSubject.createDefault<List<Location>>(emptyList())
    private val territoriesSubject = BehaviorSubject.createDefault<List<Territory>>(emptyList())
    private val territoryValidityPeriodSubject = BehaviorSubject.createDefault<ValidityPeriod>(ValidityPeriod.create(Instant.now(), Period.ofWeeks(1)))

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

    override fun setTerritoryValidityPeriod(validityPeriod: ValidityPeriod) {
        territoryValidityPeriodSubject.onNext(validityPeriod)
    }

    override fun setFootmarks(footmarks: List<Location>) {
        footmarksSubject.onNext(footmarks)
    }

    override fun setTerritories(territories: List<Territory>) {
        Timber.d(territories.map { "Territory(area=${it.area})" }.toString())
        territoriesSubject.onNext(territories)
    }

    override fun getOriginalLocation(): Observable<Location> = originalLocationSubject.hide().share()

    override fun getOrientation(): Observable<Orientation> = orientationSubject.hide().share()

    override fun getScaleFactor(): Observable<Float> = scaleFactorSubject.hide().share()

    override fun getRotateAngle(): Observable<Degree> = rotateAngleSubject.hide().share()

    override fun getFootmarks(): Observable<List<Location>> = footmarksSubject.hide().share()

    override fun getTerritoryValidityPeriod(): Observable<ValidityPeriod> = territoryValidityPeriodSubject.hide().share()

    override fun getTerritories(): Observable<List<Territory>> = territoriesSubject.hide().share()
}