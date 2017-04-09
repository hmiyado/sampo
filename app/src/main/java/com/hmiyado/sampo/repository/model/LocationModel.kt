package com.hmiyado.sampo.repository.model

import io.realm.RealmObject
import java.util.*

/**
 * Created by hmiyado on 2016/08/10.
 */
open class LocationModel(
        open var timeStamp: Date = Date(),
        open var latitude: Double = 0.0,
        open var longitude: Double = 0.0

) : RealmObject()