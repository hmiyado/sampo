package com.hmiyado.sampo.domain.math

import org.junit.Test

/**
 * Created by hmiyado on 2016/12/30.
 *
 * 平面近似法のテスト．平面近似法は十分近い距離でしか成立しないと思われるので，1km以内くらいのテストデータに対してテストする
 */
class EquirectanglarApproximationTest {
    @Test
    fun determineDistanceByTestData2() {
        MeasurementTestData.testData2.testPathwayDistanceBy(EquirectanglarApproximation)
    }

    @Test
    fun determineAzimuthByTestData2() {
        MeasurementTestData.testData2.testAzimuthBy(EquirectanglarApproximation)
    }

    @Test
    fun testPathwayToSouthWest() {
        MeasurementTestData.testPathwayToSouthWest.testPathwayDistanceBy(EquirectanglarApproximation)
        MeasurementTestData.testPathwayToSouthWest.testAzimuthBy(EquirectanglarApproximation)
    }

    @Test
    fun testPathwayToSouth() {
        MeasurementTestData.testPathwayToSouth.testPathwayDistanceBy(EquirectanglarApproximation)
        MeasurementTestData.testPathwayToSouth.testAzimuthBy(EquirectanglarApproximation)
    }

    @Test
    fun testPathwayToSouthEast() {
        MeasurementTestData.testPathwayToSouthEast.testPathwayDistanceBy(EquirectanglarApproximation)
        MeasurementTestData.testPathwayToSouthEast.testAzimuthBy(EquirectanglarApproximation)
    }

    @Test
    fun testPathwayToWest() {
        MeasurementTestData.testPathwayToWest.testPathwayDistanceBy(EquirectanglarApproximation)
        MeasurementTestData.testPathwayToWest.testAzimuthBy(EquirectanglarApproximation)
    }

    @Test
    fun testNoPathway() {
        MeasurementTestData.testNoPathway.testPathwayDistanceBy(EquirectanglarApproximation)
        MeasurementTestData.testNoPathway.testAzimuthBy(EquirectanglarApproximation)
    }

    @Test
    fun testPathwayToEast() {
        MeasurementTestData.testPathwayToEast.testPathwayDistanceBy(EquirectanglarApproximation)
        MeasurementTestData.testPathwayToEast.testAzimuthBy(EquirectanglarApproximation)
    }

    @Test
    fun testPathwayToNorthWest() {
        MeasurementTestData.testPathwayToNorthWest.testPathwayDistanceBy(EquirectanglarApproximation)
        MeasurementTestData.testPathwayToNorthWest.testAzimuthBy(EquirectanglarApproximation)
    }

    @Test
    fun testPathwayToNorth() {
        MeasurementTestData.testPathwayToNorth.testPathwayDistanceBy(EquirectanglarApproximation)
        MeasurementTestData.testPathwayToNorth.testAzimuthBy(EquirectanglarApproximation)
    }

    @Test
    fun testPathwayToNorthEast() {
        MeasurementTestData.testPathwayToNorthEast.testPathwayDistanceBy(EquirectanglarApproximation)
        MeasurementTestData.testPathwayToNorthEast.testAzimuthBy(EquirectanglarApproximation)
    }

    @Test
    fun testPathwayPositiveLongToPositiveLong() {
        MeasurementTestData.testPathwayPositiveLongToPositiveLong.testPathwayDistanceBy(EquirectanglarApproximation)
        MeasurementTestData.testPathwayPositiveLongToPositiveLong.testAzimuthBy(EquirectanglarApproximation)
    }

    @Test
    fun testPathwayNegativeLongToNegativeLong() {
        MeasurementTestData.testPathwayNegativeLongToNegativeLong.testPathwayDistanceBy(EquirectanglarApproximation)
        MeasurementTestData.testPathwayNegativeLongToNegativeLong.testAzimuthBy(EquirectanglarApproximation)
    }

    @Test
    fun testPathwayPositiveLatToPositiveLat() {
        MeasurementTestData.testPathwayPositiveLatToPositiveLat.testPathwayDistanceBy(EquirectanglarApproximation)
        MeasurementTestData.testPathwayPositiveLatToPositiveLat.testAzimuthBy(EquirectanglarApproximation)
    }

    @Test
    fun testPathwayNegativeLatToNegativeLat() {
        MeasurementTestData.testPathwayNegativeLatToNegativeLat.testPathwayDistanceBy(EquirectanglarApproximation)
        MeasurementTestData.testPathwayNegativeLatToNegativeLat.testAzimuthBy(EquirectanglarApproximation)
    }

    @Test
    fun testPathwayLong180ToLongNegative180() {
        MeasurementTestData.testPathwayLong180ToLongNegative180.testPathwayDistanceBy(EquirectanglarApproximation)
        MeasurementTestData.testPathwayLong180ToLongNegative180.testAzimuthBy(EquirectanglarApproximation)
    }

    @Test
    fun testPathwayOver180thMeridian() {
        MeasurementTestData.testPathwayOver180thMeridian.testPathwayDistanceBy(EquirectanglarApproximation)
        MeasurementTestData.testPathwayOver180thMeridian.testAzimuthBy(EquirectanglarApproximation)
    }
}