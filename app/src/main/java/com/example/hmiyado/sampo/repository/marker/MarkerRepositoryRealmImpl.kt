package com.example.hmiyado.sampo.repository.marker

import com.example.hmiyado.sampo.domain.math.toDegree
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Marker
import com.example.hmiyado.sampo.domain.model.Time.toDate
import com.example.hmiyado.sampo.domain.model.Time.toInstant
import com.example.hmiyado.sampo.domain.model.ValidityPeriod
import com.example.hmiyado.sampo.repository.model.MarkerModel
import io.realm.Realm
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import timber.log.Timber

/**
 * Created by hmiyado on 2017/04/08.
 */
class MarkerRepositoryRealmImpl : MarkerRepository {
    private fun MarkerModel.toMarker(): Marker {
        return Marker(
                Location(
                        latitude.toDegree(),
                        longitude.toDegree(),
                        markedDate.toInstant()
                ),
                ValidityPeriod.create(markedDate.toInstant(), expirationDate.toInstant())
        )
    }

    override fun saveMarker(marker: Marker) {
        Realm.getDefaultInstance().executeTransaction {
            try {
                val markerModel = it.createObject(MarkerModel::class.java)
                markerModel.latitude = marker.location.latitude.toDouble()
                markerModel.longitude = marker.location.longitude.toDouble()
                markerModel.markedDate = marker.location.timeStamp.toDate()
                markerModel.expirationDate = marker.validityPeriod.end.toDate()
            } catch (realmPrimaryKeyConstraintException: RealmPrimaryKeyConstraintException) {
                Timber.e(realmPrimaryKeyConstraintException.message)
            }
        }
    }

    override fun saveMarkers(markers: List<Marker>) {
        markers.forEach { saveMarker(it) }
    }

    override fun loadMarkers(): List<Marker> {
        return Realm.getDefaultInstance().where(MarkerModel::class.java).findAll().map {
            it.toMarker()
        }
    }
}