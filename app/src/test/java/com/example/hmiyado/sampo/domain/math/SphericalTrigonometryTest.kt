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

}