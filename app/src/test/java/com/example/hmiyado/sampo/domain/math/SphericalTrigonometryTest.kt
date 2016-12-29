package com.example.hmiyado.sampo.domain.math

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.closeTo
import org.junit.Test

/**
 * Created by hmiyado on 2016/12/28.
 *
 * 球面三角法による距離のテスト
 */
class SphericalTrigonometryTest {
    // 正解データはここで求めた
    // http://vldb.gsi.go.jp/sokuchi/surveycalc/surveycalc/bl2stf.html
    // 楕円体：GRS80
    val departureLatitude = Degree(36.0).toRadian()
    val departureLongitude = Degree(140.0).toRadian()
    val destinationLatitude = Degree(35.0).toRadian()
    val destinationLongitude = Degree(139.0).toRadian()
    val expectedDistance = 143321.578
    val expectedAzimuth = 219.566080555556
    val errorRate = 0.001

    @Test
    fun determineDistance() {
        val actualDistance = SphericalTrigonometry.determinePathwayDistance(departureLatitude, departureLongitude, destinationLatitude, destinationLongitude)
        assertThat(actualDistance, `is`(closeTo(expectedDistance, expectedDistance * errorRate)))
    }

    @Test
    fun determineAzimuth() {
        val actualAzimuth = SphericalTrigonometry.determineAzimuth(departureLatitude, departureLongitude, destinationLatitude, destinationLongitude)
        assertThat(actualAzimuth.toDegree().toDouble(), `is`(closeTo(expectedAzimuth, expectedAzimuth * errorRate)))
    }

}