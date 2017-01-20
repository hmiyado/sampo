package com.example.hmiyado.sampo.repository.location

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Time.LocalDateTime
import com.example.hmiyado.sampo.domain.model.Time.Second
import com.example.hmiyado.sampo.repository.model.LocationModel
import io.realm.Realm
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import timber.log.Timber

/**
 * Created by hmiyado on 2016/08/10.
 * Realmを使った位置情報レポジトリの実装
 */
class LocationRepositoryRealmImpl : LocationRepository {

    companion object {
        fun convertToLocationFromModel(locationModel: LocationModel): Location {
            return Location(
                    latitude = locationModel.latitude,
                    longitude = locationModel.longitude,
                    localDateTime = LocalDateTime.Companion.Factory
                            .initByUnixTime(Second(locationModel.unixTime))
                            .complete()
            )
        }
    }

    override fun saveLocation(location: Location) {
        Realm.getDefaultInstance().executeTransaction {
            try {
                val locationModel = it.createObject(LocationModel::class.java, location.localDateTime.toUnixTime().toLong())
                locationModel.latitude = location.latitude
                locationModel.longitude = location.longitude
            } catch (realmPrimaryKeyConstraintException: RealmPrimaryKeyConstraintException) {
                Timber.e(realmPrimaryKeyConstraintException.message)
            }
        }
    }

    override fun saveLocationList(locationList: List<Location>) {
        locationList.forEach {
            saveLocation(it)
        }
    }

    override fun loadLocationList(): List<Location> {
        return Realm.getDefaultInstance().where(LocationModel::class.java).findAll().map {
            convertToLocationFromModel(it)
        }
    }

    override fun loadLocationList(startLocalDateTimeInclusive: LocalDateTime, endLocalDateTimeInclusive: LocalDateTime): List<Location> {
        val startUnixTime = startLocalDateTimeInclusive.toUnixTime().toInt().toLong()
        val endUnixTime = endLocalDateTimeInclusive.toUnixTime().toInt().toLong()
        return Realm.getDefaultInstance()
                .where(LocationModel::class.java)
                .between("unixTime", startUnixTime, endUnixTime)
                .findAll()
                .map {
                    convertToLocationFromModel(it)
                }
    }
}