package com.hmiyado.sampo.domain.math

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers

/**
 * Created by hmiyado on 2016/12/30.
 *
 * @see Measurement のテストに必要なパラメータをまとめたデータクラス
 */

data class MeasurementTestData(
        val departureLatitude: Radian,
        val departureLongitude: Radian,
        val destinationLatitude: Radian,
        val destinationLongitude: Radian,
        /**
         * departure - destination 間の期待される距離(メートル)
         */
        val expectedDistance: Double,
        /**
         * departure を通る緯線から右回りに departure - destination の経路への角度(十進度数法)
         */
        val expectedAzimuth: Double,
        /**
         * 許容する期待値との誤差
         */
        val errorRate: Double
) {
    companion object {
        // 正解データはここで求めた
        // http://vldb.gsi.go.jp/sokuchi/surveycalc/surveycalc/bl2stf.html
        // 楕円体：GRS80

        // 140 km ある．出発地からみて到着地は西南方向にある(つまり，経路は西南方向である)
        val testData1 = MeasurementTestData(
                departureLatitude = Degree(36.0).toRadian(),
                departureLongitude = Degree(140.0).toRadian(),
                destinationLatitude = Degree(35.0).toRadian(),
                destinationLongitude = Degree(139.0).toRadian(),
                expectedDistance = 143321.578,
                expectedAzimuth = 219.566080555556,
                errorRate = 0.001
        )

        // 89 m，出発地からみて到着地は真東にある(つまり，経路は真東方向である)．距離が近いので，エラー率は0.005としている
        val testData2 = MeasurementTestData(
                departureLatitude = Degree(37.0).toRadian(),
                departureLongitude = Degree(139.999).toRadian(),
                destinationLatitude = Degree(37.0).toRadian(),
                destinationLongitude = Degree(140.0).toRadian(),
                expectedDistance = 89.012,
                expectedAzimuth = 89.9997,
                errorRate = 0.005
        )

        val testData3 = MeasurementTestData(
                departureLatitude = Degree(35.70320932).toRadian(),
                departureLongitude = Degree(139.78148926).toRadian(),
                destinationLatitude = Degree(35.70320448).toRadian(),
                destinationLongitude = Degree(139.78107344).toRadian(),
                expectedDistance = 37.636,
                expectedAzimuth = 269.182744444444,
                errorRate = 0.005
        )

        val testPathwayToSouthWest = MeasurementTestData(
                departureLatitude = Degree(0).toRadian(),
                departureLongitude = Degree(0).toRadian(),
                destinationLatitude = Degree(-1).toRadian(),
                destinationLongitude = Degree(-1).toRadian(),
                expectedDistance = 156899.568,
                expectedAzimuth = 225.188038888889,
                errorRate = 0.005
        )
        val testPathwayToSouth = MeasurementTestData(
                departureLatitude = Degree(0).toRadian(),
                departureLongitude = Degree(0).toRadian(),
                destinationLatitude = Degree(-1).toRadian(),
                destinationLongitude = Degree(0).toRadian(),
                expectedDistance = 110574.389,
                expectedAzimuth = 180.0,
                errorRate = 0.01
        )
        val testPathwayToSouthEast = MeasurementTestData(
                departureLatitude = Degree(0).toRadian(),
                departureLongitude = Degree(0).toRadian(),
                destinationLatitude = Degree(-1).toRadian(),
                destinationLongitude = Degree(1).toRadian(),
                expectedDistance = 156899.568,
                expectedAzimuth = 134.811961111111,
                errorRate = 0.005
        )
        val testPathwayToWest = MeasurementTestData(
                departureLatitude = Degree(0).toRadian(),
                departureLongitude = Degree(0).toRadian(),
                destinationLatitude = Degree(0).toRadian(),
                destinationLongitude = Degree(-1).toRadian(),
                expectedDistance = 111319.491,
                expectedAzimuth = 270.0,
                errorRate = 0.005
        )
        val testNoPathway = MeasurementTestData(
                departureLatitude = Degree(0).toRadian(),
                departureLongitude = Degree(0).toRadian(),
                destinationLatitude = Degree(0).toRadian(),
                destinationLongitude = Degree(0).toRadian(),
                expectedDistance = 0.0,
                expectedAzimuth = 0.0,
                errorRate = 0.005
        )
        val testPathwayToEast = MeasurementTestData(
                departureLatitude = Degree(0).toRadian(),
                departureLongitude = Degree(0).toRadian(),
                destinationLatitude = Degree(0).toRadian(),
                destinationLongitude = Degree(1).toRadian(),
                expectedDistance = 111319.491,
                expectedAzimuth = 90.0,
                errorRate = 0.005
        )
        val testPathwayToNorthWest = MeasurementTestData(
                departureLatitude = Degree(0).toRadian(),
                departureLongitude = Degree(0).toRadian(),
                destinationLatitude = Degree(1).toRadian(),
                destinationLongitude = Degree(-1).toRadian(),
                expectedDistance = 156899.568,
                expectedAzimuth = 314.811961111111,
                errorRate = 0.005
        )
        val testPathwayToNorth = MeasurementTestData(
                departureLatitude = Degree(0).toRadian(),
                departureLongitude = Degree(0).toRadian(),
                destinationLatitude = Degree(1).toRadian(),
                destinationLongitude = Degree(0).toRadian(),
                expectedDistance = 110574.389,
                expectedAzimuth = 0.0,
                errorRate = 0.01
        )
        val testPathwayToNorthEast = MeasurementTestData(
                departureLatitude = Degree(0).toRadian(),
                departureLongitude = Degree(0).toRadian(),
                destinationLatitude = Degree(1).toRadian(),
                destinationLongitude = Degree(1).toRadian(),
                expectedDistance = 156899.568,
                expectedAzimuth = 45.1880388888889,
                errorRate = 0.005
        )
        val testPathwayPositiveLongToPositiveLong = MeasurementTestData(
                departureLatitude = Degree(0).toRadian(),
                departureLongitude = Degree(1).toRadian(),
                destinationLatitude = Degree(0).toRadian(),
                destinationLongitude = Degree(2).toRadian(),
                expectedDistance = 111319.491,
                expectedAzimuth = 90.0,
                errorRate = 0.005
        )
        val testPathwayNegativeLongToNegativeLong = MeasurementTestData(
                departureLatitude = Degree(0).toRadian(),
                departureLongitude = Degree(-1).toRadian(),
                destinationLatitude = Degree(0).toRadian(),
                destinationLongitude = Degree(-2).toRadian(),
                expectedDistance = 111319.491,
                expectedAzimuth = 270.0,
                errorRate = 0.005
        )
        val testPathwayPositiveLatToPositiveLat = MeasurementTestData(
                departureLatitude = Degree(1).toRadian(),
                departureLongitude = Degree(0).toRadian(),
                destinationLatitude = Degree(2).toRadian(),
                destinationLongitude = Degree(0).toRadian(),
                expectedDistance = 110575.065,
                expectedAzimuth = 0.0,
                errorRate = 0.01
        )
        val testPathwayNegativeLatToNegativeLat = MeasurementTestData(
                departureLatitude = Degree(-1).toRadian(),
                departureLongitude = Degree(0).toRadian(),
                destinationLatitude = Degree(-2).toRadian(),
                destinationLongitude = Degree(0).toRadian(),
                expectedDistance = 110575.065,
                expectedAzimuth = 180.0,
                errorRate = 0.01
        )
        val testPathwayLong180ToLongNegative180 = MeasurementTestData(
                departureLatitude = Degree(0).toRadian(),
                departureLongitude = Degree(180).toRadian(),
                destinationLatitude = Degree(0).toRadian(),
                destinationLongitude = Degree(-180).toRadian(),
                expectedDistance = 0.0,
                expectedAzimuth = 0.0,
                errorRate = 0.005
        )

        val testPathwayOver180thMeridian = MeasurementTestData(
                departureLatitude = Degree(0).toRadian(),
                departureLongitude = Degree(180).toRadian(),
                destinationLatitude = Degree(0).toRadian(),
                destinationLongitude = Degree(-179).toRadian(),
                expectedDistance = 111319.491,
                expectedAzimuth = 90.0,
                errorRate = 0.005
        )

        /**
         * 国土地理院のAPIでは，北緯90度はIllegalArgumentになっていた
         */
        val testIllegalArgument1 = MeasurementTestData(
                departureLatitude = Degree(90).toRadian(),
                departureLongitude = Degree(0).toRadian(),
                destinationLatitude = Degree(89).toRadian(),
                destinationLongitude = Degree(0).toRadian(),
                expectedDistance = -1e100,
                expectedAzimuth = -1e10,
                errorRate = 0.005
        )
        /**
         * 国土地理院のAPIでは，北緯-90度はIllegalArgumentになっていた
         */
        val testIllegalArgument2 = MeasurementTestData(
                departureLatitude = Degree(-90).toRadian(),
                departureLongitude = Degree(0).toRadian(),
                destinationLatitude = Degree(-89).toRadian(),
                destinationLongitude = Degree(0).toRadian(),
                expectedDistance = -1e100,
                expectedAzimuth = -1e10,
                errorRate = 0.005
        )

    }

    fun determinePathwayDistanceByMeasurement(measurement: Measurement): Double {
        return measurement.determinePathwayDistance(departureLatitude, departureLongitude, destinationLatitude, destinationLongitude)
    }

    fun determineAzimuthByMeasurement(measurement: Measurement): Radian {
        return measurement.determineAzimuth(departureLatitude, departureLongitude, destinationLatitude, destinationLongitude)
    }

    fun testPathwayDistanceBy(measurement: Measurement) {
        val actualDistance = determinePathwayDistanceByMeasurement(measurement)
        MatcherAssert.assertThat(actualDistance, Matchers.`is`(Matchers.closeTo(expectedDistance, expectedDistance * errorRate)))
    }

    fun testAzimuthBy(measurement: Measurement) {
        val actualAzimuth = determineAzimuthByMeasurement(measurement)
        MatcherAssert.assertThat(actualAzimuth.toDegree().toDouble(), Matchers.`is`(Matchers.closeTo(expectedAzimuth, expectedAzimuth * errorRate)))
    }
}