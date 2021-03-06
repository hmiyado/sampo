package com.hmiyado.sampo.usecase.map.store

import com.hmiyado.sampo.domain.math.Degree
import com.hmiyado.sampo.domain.math.Measurement
import com.hmiyado.sampo.domain.model.Location
import com.hmiyado.sampo.domain.model.Marker
import com.hmiyado.sampo.domain.model.Orientation
import com.hmiyado.sampo.domain.model.Territory
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.threeten.bp.Instant

/**
 * Created by hmiyado on 2016/12/15.

 * 状態の保持/更新方法の提供/状態の提供の責務を負うクラス．
 */
class MapStoreImpl(
        override val measurement: Measurement
) : MapStore {
    private val originalLocationSubject = BehaviorSubject.createDefault<Location>(Location.empty())
    private val orientationSubject = BehaviorSubject.createDefault<Orientation>(Orientation.Companion.empty())
    private val scaleFactorSubject = BehaviorSubject.createDefault<Float>(1f)
    private val rotateAngleSubject = BehaviorSubject.createDefault<Degree>(Degree(0.0))
    private val territoriesSubject = BehaviorSubject.createDefault<List<Territory>>(emptyList())
    private val markersSubject = BehaviorSubject.createDefault<List<Marker>>(emptyList())
    private val markerLimitSubject = BehaviorSubject.createDefault(MapStore.INITIAL_MARKER_LIMIT)
    private val validityPeriodEndSubject = BehaviorSubject.createDefault(Instant.EPOCH)

    override val updatedMarkersSignal: Observable<List<Marker>>
        get() = markersSubject.hide().share()

    override val updatedMarkerLimitSignal: Observable<Int>
        get() = markerLimitSubject.hide().share()

    override val updatedValidityPeriodEndSignal: Observable<Instant>
        get() = validityPeriodEndSubject.hide().share()

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

    override fun setTerritories(territories: List<Territory>) {
        territoriesSubject.onNext(territories)
    }


    override fun setMarkers(markers: List<Marker>) {
        markersSubject.onNext(markers)
    }

    override fun setValidityPeriodEnd(end: Instant) {
        validityPeriodEndSubject.onNext(end)
    }

    override fun getOriginalLocation(): Observable<Location> = originalLocationSubject.hide().share()

    override fun getOrientation(): Observable<Orientation> = orientationSubject.hide().share()

    override fun getScaleFactor(): Observable<Float> = scaleFactorSubject.hide().share()

    override fun getRotateAngle(): Observable<Degree> = rotateAngleSubject.hide().share()

    override fun getTerritories(): Observable<List<Territory>> = territoriesSubject.hide().share()
}