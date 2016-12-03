package com.example.hmiyado.sampo.domain.math

import com.example.hmiyado.sampo.domain.model.Location
import com.example.hmiyado.sampo.repository.location.LocationDistanceInterface

/**
 * Created by hmiyado on 2016/09/22.
 *
 * ヒュベニの公式を用いて緯度経度に基づく距離計算をするためのオブジェクト
 *
 * 参考URL
 * http://tancro.e-central.tv/grandmaster/excel/hubenystandard.html
 * http://yamadarake.jp/trdi/report000001.html
 */
object HubenyFormula : LocationDistanceInterface {

    val a: Double = 6377397.155 // 長半径(赤道半径)
    val b: Double = 6356079.000 // 短半径(極半径)
    val e: Double = Math.sqrt((a * a - b * b) / (a * a)) // 第一離心率
    val numeratorOfM = a * (1 - e * e) // M の分子


    private fun determineMu(latitude1: Double, latitude2: Double): Double {
        return (latitude1 + latitude2) / 2
    }

    private fun determineW(mu: Double): Double {
        val sin = Math.sin(mu)
        return Math.sqrt(1 - (e * sin).square())
    }

    private fun determineN(W: Double): Double {
        return a / W
    }

    private fun determineM(W: Double): Double {
        return numeratorOfM / W.cube()
    }

    /**
     * 地点p1 と 地点p2 の距離を計算する．
     * 単位はメートル
     *
     * @param p1 地点１
     * @param p2 地点２
     */
    override fun determineDistance(p1: Location, p2: Location): Double {
        val dX = p1.longitude - p2.longitude
        val dY = p1.latitude - p2.latitude

        val mu = determineMu(p1.latitude, p2.latitude)
        val W = determineW(mu)
        val M = determineM(W)
        val N = determineN(W)
        return Math.sqrt((dY * M).square() + (dX * N * Math.cos(mu)).square())
    }
}