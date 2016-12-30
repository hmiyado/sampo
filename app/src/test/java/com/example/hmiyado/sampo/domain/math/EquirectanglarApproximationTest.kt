package com.example.hmiyado.sampo.domain.math

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
}