package com.example.hmiyado.sampo.repository.location

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.domain.model.Time.LocalDateTime
import com.example.hmiyado.sampo.domain.model.Time.Second
import com.example.hmiyado.sampo.repository.model.LocationModel
import io.realm.Realm
import io.realm.RealmConfiguration.Builder
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import timber.log.Timber

/**
 * Created by hmiyado on 2016/08/10.
 * Realmを使った位置情報レポジトリの実装
 */
class LocationRepositoryRealmImpl() : LocationRepository {

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

    val realm: Realm

    init {
        val realmConfig = Builder()
                .name("Location.realm")
                .schemaVersion(1)
                .build()
        realm = Realm.getInstance(realmConfig)
    }

    override fun saveLocation(location: Location) {
        // TODO 非同期に保存できるようにする
        // executeTransactionAsync はあるが，何も考えずに使うとエラーをはく
        realm.executeTransaction {
            try {
                val locationModel = realm.createObject(LocationModel::class.java)
                locationModel.unixTime = location.localDateTime.toUnixTime().toLong()
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
        return realm.where(LocationModel::class.java).findAll().map {
            convertToLocationFromModel(it)
        }
    }

    override fun loadLocationList(startLocalDateTimeInclusive: LocalDateTime, endLocalDateTimeInclusive: LocalDateTime): List<Location> {
        val startUnixTime = startLocalDateTimeInclusive.toUnixTime().toInt().toLong()
        val endUnixTime = endLocalDateTimeInclusive.toUnixTime().toInt().toLong()
        return realm
                .where(LocationModel::class.java)
                .between("unixTime", startUnixTime, endUnixTime)
                .findAll()
                .map {
                    convertToLocationFromModel(it)
                }
    }
}