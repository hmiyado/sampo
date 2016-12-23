package com.example.hmiyado.sampo.domain.model

import com.example.hmiyado.sampo.domain.math.Degree

/**
 * Created by hmiyado on 2016/12/01.
 * 端末の向きを定義するためのデータクラス
 *
 * 端末の画面に対して垂直な直線をz軸，端末の上端と下端を結ぶ直線をy軸，端末の右端と左端を結ぶ直線をx軸とする．
 * 端末が地面と並行でかつ画面が地面と反対側にあり，端末上端が北側，下端が南側にあるとき，azimuth=0, pitch=0, roll=0．
 *
 * @param azimuth 端末のz軸を中心とする回転角．北向きのとき0．東向きのとき90度．南向きのとき180度．値域は0から360度．
 * @param pitch 端末のx軸を中心とする回転角．端末上端が地面を向いているとき180度,端末が水平のとき0，端末下端が地面を向いているとき-180度．値域は-180度から180度．
 * @param roll 端末のy軸を中心とする回転角．端末左端が地面側にあるとき-90度，端末が地面と平行なとき0，端末右端が地面側にあるとき90度．値域は-90度から90度
 */
class Orientation(
        /**
         * 端末のz軸を中心とする回転角．北向きのとき0．東向きのとき90度．南向きのとき180度．値域は0から360度．
         */
        val azimuth: Degree,
        /**
         * 端末のx軸を中心とする回転角．端末上端が地面を向いているとき180度,端末が水平のとき0，端末下端が地面を向いているとき-180度．値域は-180度から180度．
         */
        val pitch: Degree,
        /**
         * 端末のy軸を中心とする回転角．端末左端が地面側にあるとき-90度，端末が地面と平行なとき0，端末右端が地面側にあるとき90度．値域は-90度から90度
         */
        val roll: Degree
) {

    companion object {
        /**
         * @param array orientation に変換する値が入っているarray．array[0]がazimuth, array[1]がpitch,array[2]がrollになる．
         * @throws IllegalArgumentException arrayのサイズが3未満だと投げられる．
         */
        fun fromFloatArray(array: FloatArray): Orientation {
            if (array.size < 3) {
                throw IllegalArgumentException("array size must be larger than 3 but ${array.size}")
            }

            return Orientation(azimuth = Degree(array[0].toDouble()), pitch = Degree(array[1].toDouble()), roll = Degree(array[2].toDouble()))
        }

        fun empty(): Orientation {
            return Orientation(Degree(0.0), Degree(0.0), Degree(0.0))
        }
    }

    override fun toString(): String {
        return "Orientation(azimuth=$azimuth, pitch=$pitch, roll=$roll)"
    }
}