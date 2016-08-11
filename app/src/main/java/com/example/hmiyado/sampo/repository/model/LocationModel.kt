package com.example.hmiyado.sampo.repository.model

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey

/**
 * Created by hmiyado on 2016/08/10.
 */
open class LocationModel(
        @PrimaryKey open var unixTime: Long = 0,
        open var latitude: Double = 0.0,
        open var longitude: Double = 0.0

) : RealmObject() {
}