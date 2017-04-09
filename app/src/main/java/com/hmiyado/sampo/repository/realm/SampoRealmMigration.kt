package com.hmiyado.sampo.repository.realm

import io.realm.DynamicRealm
import io.realm.RealmMigration
import java.util.*

/**
 * Created by hmiyado on 2017/04/09.
 */
object SampoRealmMigration : RealmMigration {
    override fun migrate(realm: DynamicRealm?, oldVersion: Long, newVersion: Long) {
        val schema = realm?.schema ?: return

        (oldVersion..newVersion).forEach {
            when (it) {
                1L -> {
                    schema.create("LocationModel")
                            .addField("latitude", Double::class.java)
                            .addField("longitude", Double::class.java)
                            .addField("timeStamp", Date::class.java)
                }
                2L -> {
                    schema.create("MarkerModel")
                            .addField("latitude", Double::class.java)
                            .addField("longitude", Double::class.java)
                            .addField("markedDate", Date::class.java)
                            .addField("expirationDate", Date::class.java)
                }
            }
        }
    }
}