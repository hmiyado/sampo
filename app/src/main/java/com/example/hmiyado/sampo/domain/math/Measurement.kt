package com.example.hmiyado.sampo.domain.math

import com.example.hmiyado.sampo.domain.model.Location

/**
 * Created by hmiyado on 2016/12/28.
 *
 * 距離と方位角を測るインターフェース
 */
interface Measurement {
    companion object {
        /**
         * GRS80にもとづく赤道半径．単位はメートル
         */
        val a = 6378137.0
        /**
         * GRS80にもとづく極半径．単位はメートル
         */
        val b: Double = 6356752.31414 // 短半径(極半径)
        /**
         * 平均半径．単位はメートル．
         * @see <a href="https://en.wikipedia.org/wiki/Earth_radius#Mean_radius">英語版Wiki</a>
         */
        val MEAN_RADIUS = (2 * a + b) / 3

        /**
         * @return 出発点から到着点までの経路が180度子午線を超えたら true
         */
        fun isOver180thMeridian(departureLongitude: Radian, destinationLongitude: Radian): Boolean {
            return departureLongitude >= Radian.PI / 2 && destinationLongitude <= -Radian.PI / 2
        }
    }


    /**
     * 出発地から到着地への最短経路の長さを求める．
     * 単位はメートル
     */
    fun determinePathwayDistance(
            departureLatitude: Radian,
            departureLongitude: Radian,
            destinationLatitude: Radian,
            destinationLongitude: Radian
    ): Double

    /**
     * 緯線から右回りに，出発地(departure)から到着地(destination)への最短経路がなす角を求める．
     * 値域は0以上2*pi未満
     * なので，出発地が到着地より西にあれば，返り値は0より大きくpiより小さな値になる．
     * 逆に，出発地が到着地より東にあれば，返り値はpiより大きく2*piより小さな値になる．
     */
    fun determineAzimuth(
            departureLatitude: Radian,
            departureLongitude: Radian,
            destinationLatitude: Radian,
            destinationLongitude: Radian
    ): Radian

    fun determinePathwayDistance(departure: Location, destination: Location): Double {
        return determinePathwayDistance(
                departure.latitude.toDegree().toRadian(),
                departure.longitude.toDegree().toRadian(),
                destination.latitude.toDegree().toRadian(),
                destination.longitude.toDegree().toRadian()
        )
    }

    fun determineAzimuth(departure: Location, destination: Location): Radian {
        return determineAzimuth(
                departure.latitude.toDegree().toRadian(),
                departure.longitude.toDegree().toRadian(),
                destination.latitude.toDegree().toRadian(),
                destination.longitude.toDegree().toRadian()
        )
    }
}