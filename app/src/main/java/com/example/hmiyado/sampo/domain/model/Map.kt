package com.example.hmiyado.sampo.domain.model

import com.example.hmiyado.sampo.domain.math.Degree

/**
 * Created by hmiyado on 2016/12/15.
 *
 * 地図の情報のモデル．
 *
 * @param originalLocation 地図の原点．普通はユーザーの位置？
 * @param orientation ユーザーの向いている方向
 * @param scaleFactor 地図の縮尺倍率
 * @param rotateAngle 地図が北からどれだけ回転して表示されているかを表す回転角(度数法)
 * @param footmarks これまでに訪れた地点のリスト
 */
class Map(
        val originalLocation: Location,
        val orientation: Orientation,
        /**
         * 地図の縮尺倍率
         */
        val scaleFactor: Float,
        /**
         * 地図が北からどれだけ回転して表示されているかを表す回転角(度数法)
         */
        val rotateAngle: Degree,
        /**
         * これまでに訪れた地点
         */
        val footmarks: List<Location>

) {
    companion object {
        /**
         * 倍率１のときの，100 px あたりの地図上の距離（メートル）
         */
        val SCALE_UNIT = 100

        fun empty(): Map {
            return Map(Location.empty(), Orientation.empty(), 1f, Degree(0.0), emptyList())
        }
    }

    /**
     * 地図の縮尺
     */
    val scale: Float
        get() = scaleFactor * SCALE_UNIT

    override fun toString(): String {
        return "Map(originalLocation=$originalLocation, orientation=$orientation, scaleFactor=$scaleFactor, rotateAngle=$rotateAngle, footmarks=${footmarks.size}marks)"
    }

    class Builder(map: Map) {
        private var originalLocation = map.originalLocation
        private var orientation = map.orientation
        private var scaleFactor = map.scaleFactor
        private var rotateAngle = map.rotateAngle
        private var footmarks = map.footmarks

        fun build(): Map {
            return Map(
                    originalLocation,
                    orientation,
                    scaleFactor,
                    rotateAngle,
                    footmarks
            )
        }

        fun setOriginalLocation(originalLocation: Location): Builder {
            this.originalLocation = originalLocation
            return this
        }

        fun setOrientation(orientation: Orientation): Builder {
            this.orientation = orientation
            return this
        }

        fun setScaleFactor(scaleFactor: Float): Builder {
            this.scaleFactor = scaleFactor
            return this
        }

        fun setRotateAngle(rotateAngle: Degree): Builder {
            this.rotateAngle = rotateAngle
            return this
        }

        fun setFootmarks(footmarks: List<Location>): Builder {
            this.footmarks = footmarks
            return this
        }

        fun addFootmark(footmark: Location): Builder {
            this.footmarks += footmark
            return this
        }
    }
}