package com.hmiyado.sampo.repository.model

import io.realm.RealmObject
import java.util.*

/**
 * Created by hmiyado on 2017/04/08.
 */
open class MarkerModel(
        open var markedDate: Date = Date(),
        open var latitude: Double = 0.0,
        open var longitude: Double = 0.0,
        open var expirationDate: Date = Date()
) : RealmObject()