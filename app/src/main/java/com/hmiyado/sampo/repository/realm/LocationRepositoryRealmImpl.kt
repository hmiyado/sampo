package com.hmiyado.sampo.repository.realm

import com.hmiyado.sampo.domain.math.toDegree
import com.hmiyado.sampo.domain.model.Location
import com.hmiyado.sampo.domain.model.Time.toDate
import com.hmiyado.sampo.domain.model.Time.toInstant
import com.hmiyado.sampo.repository.location.LocationRepository
import com.hmiyado.sampo.repository.model.LocationModel
import io.realm.Realm
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import org.threeten.bp.Instant
import timber.log.Timber

/**
 * Created by hmiyado on 2016/08/10.
 * Realmを使った位置情報レポジトリの実装
 */
class LocationRepositoryRealmImpl : LocationRepository {
    private fun LocationModel.toLocation(): Location {
        return Location(
                latitude = latitude.toDegree(),
                longitude = longitude.toDegree(),
                timeStamp = timeStamp.toInstant()
        )
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
            it.toLocation()
        }
    }

    override fun loadLocationList(begin: Instant, end: Instant): List<Location> {
        return Realm.getDefaultInstance()
                .where(LocationModel::class.java)
                .between("timeStamp", begin.toDate(), end.toDate())
                .findAll()
                .map {
                    it.toLocation()
                }
    }

    override fun loadLocationList(begin: Instant): List<Location> {
        return Realm.getDefaultInstance()
                .where(LocationModel::class.java)
                .greaterThan("timeStamp", begin.toDate())
                .findAll()
                .map {
                    it.toLocation()
                }
    }

}