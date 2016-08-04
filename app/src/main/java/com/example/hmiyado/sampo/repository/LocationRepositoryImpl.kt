package com.example.hmiyado.sampo.repository

import android.content.Context
import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.kotlin.Time.LocalDateTime
import com.example.hmiyado.sampo.kotlin.Time.Second
import java.io.File

/**
 * Created by hmiyado on 2016/08/05.
 */
class LocationRepositoryImpl(
        private val context: Context
) :LocationRepository{
    companion object{
        fun locationToCsv(location: Location): String {
            return "${location.latitude},${location.longitude},${location.localDateTime.toUnixTime()}"
        }
        fun parseLocationFromCsv(line: String): Location {
            val elements = line.split(",")
            val latitude = elements[0].toDouble()
            val longitude = elements[1].toDouble()
            val unixTime = elements[2].toInt()
            return Location(latitude, longitude, LocalDateTime.Companion.Factory.initByUnixTime(Second(unixTime)).complete())
        }
    }

    private val repositoryName = "location_list.csv"
    private val file = File(context.filesDir, repositoryName)

    override fun saveLocation(location: Location) {
        file.writeText(locationToCsv(location)+"¥n")
    }

    override fun loadLocationList(): List<Location> {
        return file.readLines().map { parseLocationFromCsv(it) }
    }

    override fun loadLocationList(startLocalDateTimeInclusive: LocalDateTime, endLocalDateTimeInclusive: LocalDateTime): List<Location> {
        return file.readLines()
                .map { parseLocationFromCsv(it) }
                .filter { it.localDateTime >= startLocalDateTimeInclusive && it.localDateTime <= endLocalDateTimeInclusive }
    }
}