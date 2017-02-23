package com.example.hmiyado.sampo.repository.location

import com.example.hmiyado.sampo.domain.model.Location
import org.threeten.bp.Instant

/**
 * Created by hmiyado on 2016/08/04.
 * 位置情報を取得するレポジトリ
 */
interface LocationRepository {
    fun saveLocation(location: Location)
    fun saveLocationList(locationList: List<Location>)
    fun loadLocationList(): List<Location>
    fun loadLocationList(begin: Instant, end: Instant): List<Location>
}