package com.example.hmiyado.sampo.repository.location

import com.example.hmiyado.sampo.domain.math.toDegree
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Time.toDate
import com.example.hmiyado.sampo.domain.model.Time.toInstant
import com.example.hmiyado.sampo.repository.model.LocationModel
import io.realm.Realm
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import org.threeten.bp.Instant
import timber.log.Timber

/**
 * Created by hmiyado on 2016/08/10.
 * Realmを使った位置情報レポジトリの実装
 */
class LocationRepositoryRealmImpl : LocationRepository {

    companion object {
        fun convertToLocationFromModel(locationModel: LocationModel): Location {
            return Location(
                    latitude = locationModel.latitude.toDegree(),
                    longitude = locationModel.longitude.toDegree(),
                    timeStamp = locationModel.timeStamp.toInstant()
            )
        }
    }

    override fun saveLocation(location: Location) {
        Realm.getDefaultInstance().executeTransaction {
            try {
                val locationModel = it.createObject(LocationModel::class.java)
                locationModel.latitude = location.latitude.toDouble()
                locationModel.longitude = location.longitude.toDouble()
                locationModel.timeStamp = location.timeStamp.toDate()
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

    override fun loadLocationList(begin: Instant, end: Instant): List<Location> {
        return Realm.getDefaultInstance()
                .where(LocationModel::class.java)
                .between("timeStamp", begin.toDate(), end.toDate())
                .findAll()
                .map {
                    convertToLocationFromModel(it)
                }
    }
}