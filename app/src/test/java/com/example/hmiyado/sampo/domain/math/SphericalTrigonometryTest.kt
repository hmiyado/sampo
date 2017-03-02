package com.example.hmiyado.sampo.domain.math

import org.junit.Test

/**
 * Created by hmiyado on 2016/12/28.
 *
 * 球面三角法による距離のテスト
 */
class SphericalTrigonometryTest {
    @Test
    fun determineDistanceByTestData1() {
        MeasurementTestData.testData1.testPathwayDistanceBy(SphericalTrigonometry)
    }

    @Test
    fun determineAzimuthByTestData1() {
        MeasurementTestData.testData1.testAzimuthBy(SphericalTrigonometry)
    }


    @Test
    fun determineDistanceByTestData2() {
        MeasurementTestData.testData2.testPathwayDistanceBy(SphericalTrigonometry)
    }

    @Test
    fun determineAzimuthByTestData2() {
        MeasurementTestData.testData2.testAzimuthBy(SphericalTrigonometry)
    }

    @Test
    fun testByTestData3() {
        MeasurementTestData.testData3.testPathwayDistanceBy(SphericalTrigonometry)
    }

    @Test
    fun testPathwayToSouthWest() {
        MeasurementTestData.testPathwayToSouthWest.testPathwayDistanceBy(SphericalTrigonometry)
        MeasurementTestData.testPathwayToSouthWest.testAzimuthBy(SphericalTrigonometry)
    }

    @Test
    fun testPathwayToSouth() {
        MeasurementTestData.testPathwayToSouth.testPathwayDistanceBy(SphericalTrigonometry)
        MeasurementTestData.testPathwayToSouth.testAzimuthBy(SphericalTrigonometry)
    }

    @Test
    fun testPathwayToSouthEast() {
        MeasurementTestData.testPathwayToSouthEast.testPathwayDistanceBy(SphericalTrigonometry)
        MeasurementTestData.testPathwayToSouthEast.testAzimuthBy(SphericalTrigonometry)
    }

    @Test
    fun testPathwayToWest() {
        MeasurementTestData.testPathwayToWest.testPathwayDistanceBy(SphericalTrigonometry)
        MeasurementTestData.testPathwayToWest.testAzimuthBy(SphericalTrigonometry)
    }

    @Test
    fun testNoPathway() {
        MeasurementTestData.testNoPathway.testPathwayDistanceBy(SphericalTrigonometry)
        MeasurementTestData.testNoPathway.testAzimuthBy(SphericalTrigonometry)
    }

    @Test
    fun testPathwayToEast() {
        MeasurementTestData.testPathwayToEast.testPathwayDistanceBy(SphericalTrigonometry)
        MeasurementTestData.testPathwayToEast.testAzimuthBy(SphericalTrigonometry)
    }

    @Test
    fun testPathwayToNorthWest() {
        MeasurementTestData.testPathwayToNorthWest.testPathwayDistanceBy(SphericalTrigonometry)
        MeasurementTestData.testPathwayToNorthWest.testAzimuthBy(SphericalTrigonometry)
    }

    @Test
    fun testPathwayToNorth() {
        MeasurementTestData.testPathwayToNorth.testPathwayDistanceBy(SphericalTrigonometry)
        MeasurementTestData.testPathwayToNorth.testAzimuthBy(SphericalTrigonometry)
    }

    @Test
    fun testPathwayToNorthEast() {
        MeasurementTestData.testPathwayToNorthEast.testPathwayDistanceBy(SphericalTrigonometry)
        MeasurementTestData.testPathwayToNorthEast.testAzimuthBy(SphericalTrigonometry)
    }

    @Test
    fun testPathwayPositiveLongToPositiveLong() {
        MeasurementTestData.testPathwayPositiveLongToPositiveLong.testPathwayDistanceBy(SphericalTrigonometry)
        MeasurementTestData.testPathwayPositiveLongToPositiveLong.testAzimuthBy(SphericalTrigonometry)
    }

    @Test
    fun testPathwayNegativeLongToNegativeLong() {
        MeasurementTestData.testPathwayNegativeLongToNegativeLong.testPathwayDistanceBy(SphericalTrigonometry)
        MeasurementTestData.testPathwayNegativeLongToNegativeLong.testAzimuthBy(SphericalTrigonometry)
    }

    @Test
    fun testPathwayPositiveLatToPositiveLat() {
        MeasurementTestData.testPathwayPositiveLatToPositiveLat.testPathwayDistanceBy(SphericalTrigonometry)
        MeasurementTestData.testPathwayPositiveLatToPositiveLat.testAzimuthBy(SphericalTrigonometry)
    }

    @Test
    fun testPathwayNegativeLatToNegativeLat() {
        MeasurementTestData.testPathwayNegativeLatToNegativeLat.testPathwayDistanceBy(SphericalTrigonometry)
        MeasurementTestData.testPathwayNegativeLatToNegativeLat.testAzimuthBy(SphericalTrigonometry)
    }

    @Test
    fun testPathwayLong180ToLongNegative180() {
        MeasurementTestData.testPathwayLong180ToLongNegative180.testPathwayDistanceBy(SphericalTrigonometry)
        MeasurementTestData.testPathwayLong180ToLongNegative180.testAzimuthBy(SphericalTrigonometry)
    }

    @Test
    fun testPathwayOver180thMeridian() {
        MeasurementTestData.testPathwayOver180thMeridian.testPathwayDistanceBy(SphericalTrigonometry)
        MeasurementTestData.testPathwayOver180thMeridian.testAzimuthBy(SphericalTrigonometry)
    }
}